package com.mobdeve.s15.gironasayasranario.cinereview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.MovieSelectBinding
import com.mobdeve.s15.gironasayasranario.cineview.Movie

class MovieAdapter(val data: ArrayList<Movie>, val movieActivityResultLauncher: ActivityResultLauncher<Intent>) : Adapter<MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemViewBinding: MovieSelectBinding = MovieSelectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieHolder(itemViewBinding.root)
    }

    /*
    *   TODO: Complete the onBindViewHolder logic.
    * */
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindData(data.get(position))
    }

    /*
    *   TODO: Complete the onBindViewHolder logic.
    * */
    override fun getItemCount(): Int {
        return data.size
    }
}