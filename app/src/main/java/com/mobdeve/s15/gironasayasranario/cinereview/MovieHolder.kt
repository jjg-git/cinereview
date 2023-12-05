package com.mobdeve.s15.gironasayasranario.cinereview

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.MovieSelectBinding
import com.mobdeve.s15.gironasayasranario.cineview.Movie

class MovieHolder(viewBind: MovieSelectBinding) : ViewHolder(viewBind.root){
    private val TAG = "MovieHolder"
    var viewBind = viewBind
    lateinit var movie: Movie
    fun bindData(movie : Movie) {
        viewBind.movieIv.setImageResource(movie.thumbnail)
        viewBind.nameTv.setText(movie.name)
    }
}