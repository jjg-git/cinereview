package com.mobdeve.s15.gironasayasranario.cineview

class Movie (
    val name: String,
    val genre: String = "",
    val casts: ArrayList<String> = ArrayList<String>(),
    val directors: ArrayList<String> = ArrayList<String>(),
    val promoPics: ArrayList<String> = ArrayList<String>(),
    val rating: String = "",
    val thumbnail: Int,
)