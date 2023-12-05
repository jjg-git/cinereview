package com.mobdeve.s15.gironasayasranario.cinereview

import com.google.firebase.firestore.GeoPoint

data class Cinema(
    val name: String = "",
    val imageUrl: String = "",
    val location: GeoPoint? = null, // Use GeoPoint type for location
    var distanceToUser: Double = 0.0 // To store distance from user
)
