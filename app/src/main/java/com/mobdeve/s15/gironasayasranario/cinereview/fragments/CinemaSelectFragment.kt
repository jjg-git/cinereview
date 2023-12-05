package com.mobdeve.s15.gironasayasranario.cinereview.fragments

import CinemaAdapter
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.mobdeve.s15.gironasayasranario.cinereview.Cinema
import com.mobdeve.s15.gironasayasranario.cinereview.DBController
import com.mobdeve.s15.gironasayasranario.cinereview.R
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.FragmentCinemaSelectBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_CINEMA_NAME = "cinema_name"
//private const val ARG_DISTANCE = "distance"

/**
 * A simple [Fragment] subclass.
 * Use the [CinemaSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CinemaSelectFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var cinema_name: String? = null
//    private var param2: String? = null
    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
//        permissions ->
////        if (ContextCompat.checkSelfPermission(this.applicationContext,
////                android.Manifest.permission.ACCESS_FINE_LOCATION)
////            == PackageManager.PERMISSION_GRANTED) {
//        if (isGranted) {
//            getLastLocation()
//        } else {
//
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
//        }
        permissions ->
            permissions.entries.forEach {
                Log.d("DEBUG", "${it.key} = ${it.value}")
                if (!it.value){
                    return@registerForActivityResult
                }
            }
    }

    private val TAG: String? = "CinemaSelectFragment"
    private lateinit var cinemaAdapter: CinemaAdapter
    private lateinit var binding: FragmentCinemaSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let {
//            cinema_name = it.getString(ARG_CINEMA_NAME)
//            param2 = it.getString(ARG_DISTANCE)
//        }
        Log.d("DEBUG", requestPermissionLauncher.launch())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCinemaSelectBinding.inflate(inflater)
//        initializeRecyclerView()
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


    private fun setupCinemaRecyclerView() {
        // Initially, set an empty adapter
        cinemaAdapter = CinemaAdapter(emptyList())
        binding.cinemaRv.apply {
            adapter = cinemaAdapter
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

    }
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

//    fun getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this.applicationContext,
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//            fusedLocationProviderClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//
//                    if (location != null) {
//                        // Logic to handle location object
//                        fetchCinemas(location)
//                    }
//                }
//        } else {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                PackageManager.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
//        }
//    }
}