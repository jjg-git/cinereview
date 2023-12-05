package com.mobdeve.s15.gironasayasranario.cinereview

import com.mobdeve.s15.gironasayasranario.cineview.Movie

class DataGenerator {
    companion object {
        fun generateData() : ArrayList<Movie> {
            var data = ArrayList<Movie>()
            data.add(Movie("Bluey", thumbnail = R.drawable.bluey, rating="G"))
            data.add(Movie("Adventure Time", thumbnail = R.drawable.adventure_time, rating="PG"))
            data.add(Movie("Avatar: The Last Airbender", thumbnail = R.drawable.avatar, rating="PG"))
            data.add(Movie("We Bare Bears", thumbnail = R.drawable.webarebears, rating="G",
                description = """
                Three bear brothers do whatever they can to be a part of 
                human society by doing what everyone around them does.
                """.trimIndent()))
            data.add(Movie("Jujutsu Kaisen 0", thumbnail = R.drawable.jujutsukaisen0, rating="SPG"))
            return data
        }

    }
}