package com.mobdeve.s15.gironasayasranario.cinereview.fragments

import CinemaAdapter
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.LocationServices
import com.mobdeve.s15.gironasayasranario.cinereview.Cinema
import com.mobdeve.s15.gironasayasranario.cinereview.DBController
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.FragmentCinemaSelectBinding

class CinemaSelectFragment : Fragment() {
    private val TAG: String? = "CinemaSelectFragment"
    private lateinit var cinemaAdapter: CinemaAdapter
    private lateinit var binding: FragmentCinemaSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cinemaAdapter = CinemaAdapter(emptyList())
        requestPermissionLauncher.launch(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCinemaSelectBinding.inflate(inflater)
        binding.cinemaRv.adapter = cinemaAdapter

        return binding.root
    }

    private fun fetchCinemasFromFirestore() {
        DBController.getData("cinemas")
//        db.collection("cinemas").get()
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
        DBController.getData("cinemas")
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

    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissions ->
        when {
            permissions.getOrDefault(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                false
            ) -> {
                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        fetchCinemas(location)
                    }
                }
            }

            permissions.getOrDefault(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                false
            ) -> {
                Log.d("DEBUG", "Accepted course location")
            }
        }
    }

//    fun checkPermissions() {
//        if (ContextCompat.checkSelfPermission(this.applicationContext,
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//            getLastLocation()
//        } else {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
//        }

//}
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    getLastLocation()
//                }
//            }
//        }
//    }
}