package com.mobdeve.s15.gironasayasranario.cinereview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.ActivityMainBinding
import com.mobdeve.s15.gironasayasranario.cineview.Movie

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        initializeRecyclerView()
        setContentView(binding.root)
        checkPermissions()
        setupCinemaRecyclerView() // Set up the RecyclerView
        fetchCinemasFromFirestore()// Fetch cinemas from Firestore

        movieRecycler = binding.movieRv

        this.data = DataGenerator.generateData()
        this.movieLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movieRecycler.layoutManager = movieLayout
        movieRecycler.adapter = MovieAdapter(data, movieLauncher)

        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initializeRecyclerView() {

        cinemaAdapter = CinemaAdapter(emptyList())
        binding.cinemaRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.cinemaRv.adapter = cinemaAdapter
    }

    private fun fetchCinemasFromFirestore() {
        db.collection("cinemas").get()
            .addOnSuccessListener { result ->
                val cinemasList = result.mapNotNull { document ->
                    document.toObject(Cinema::class.java).also {
                        Log.d(TAG, "Fetched cinema: ${it.name}")
                    }
                }
                Log.d(TAG, "Total cinemas fetched: ${cinemasList.size}")
                if (cinemasList.isNotEmpty()) {
                    cinemaAdapter.cinemas = cinemasList
                    cinemaAdapter.notifyDataSetChanged()
                } else {
                    Log.d(TAG, "Cinema list is empty")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents: ", exception)
            }
    }

    fun fetchCinemas(userLocation: Location) {
        db.collection("cinemas").get()
            .addOnSuccessListener { documents ->
                val cinemasList = documents.mapNotNull { document ->
                    val cinema = document.toObject(Cinema::class.java)
                    cinema.location?.let {
                        val cinemaLocation = Location("").apply {
                            latitude = it.latitude
                            longitude = it.longitude
                        }
                        cinema.distanceToUser = userLocation.distanceTo(cinemaLocation) / 1000.0 // Convert the result to Double

                        cinema
                    }
                }.sortedBy { it.distanceToUser }

                cinemaAdapter.updateCinemas(cinemasList)
            }
    }






    private fun updateCinemaRecyclerView(cinemasList: List<Cinema>) {
        Log.d(TAG, "Updating RecyclerView with cinemas: $cinemasList")
        cinemaAdapter.updateCinemas(cinemasList)
        Log.d(TAG, "Adapter item count after update: ${cinemaAdapter.itemCount}")
    }



    private fun setupCinemaRecyclerView() {
        // Initially, set an empty adapter
        cinemaAdapter = CinemaAdapter(emptyList())
        binding.cinemaRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = cinemaAdapter
        }
    }
    fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            getLastLocation()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLastLocation()
                }
            }
        }
    }

    fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->

                    if (location != null) {
                        // Logic to handle location object
                        fetchCinemas(location)
                    }
                }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }


}
