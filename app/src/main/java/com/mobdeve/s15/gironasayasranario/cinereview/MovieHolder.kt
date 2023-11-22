package com.mobdeve.s15.gironasayasranario.cinereview

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.MovieSelectBinding
import com.mobdeve.s15.gironasayasranario.cineview.Movie

class MovieHolder(viewBind: MovieSelectBinding) : ViewHolder(viewBind.root){
    var viewBind = viewBind

    fun bindData(movie : Movie) {
        viewBind.movieIv.setImageResource(movie.thumbnail)
        viewBind.nameTv.setText(movie.name)
    }
}