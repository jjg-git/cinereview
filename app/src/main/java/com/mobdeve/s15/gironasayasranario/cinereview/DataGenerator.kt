package com.mobdeve.s15.gironasayasranario.cinereview

import com.mobdeve.s15.gironasayasranario.cineview.Movie

class DataGenerator {
    companion object {
        fun generateData() : ArrayList<Movie> {
            var data = ArrayList<Movie>()
            data.add(Movie("Bluey", thumbnail = R.drawable.bluey))
            data.add(Movie("Adventure Time", thumbnail = R.drawable.adventure_time))
            data.add(Movie("Avatar: The Last Airbender", thumbnail = R.drawable.avatar))
            data.add(Movie("We Bare Bears", thumbnail = R.drawable.webarebears))
            data.add(Movie("Jujutsu Kaisen 0", thumbnail = R.drawable.jujutsukaisen0))
            return data
        }

    }
}