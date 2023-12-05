package com.mobdeve.s15.gironasayasranario.cinereview

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.FragmentMainBinding
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.MovieSelectBinding
import com.mobdeve.s15.gironasayasranario.cineview.Movie

class MovieAdapter(val data: ArrayList<Movie>, val navController: NavController) : Adapter<MovieHolder>() {
    private val TAG = "MovieAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemViewBinding: MovieSelectBinding = MovieSelectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val holder = MovieHolder(itemViewBinding)
        return holder
    }

    /*
    *   TODO: Complete the onBindViewHolder logic.
    * */
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = data.get(position)
        val bundle = bundleOf(
            "name" to movie.name,
            "description" to movie.description,
            "cast" to movie.casts,
            "director" to movie.directors,
            "rating" to movie.rating,
            "poster" to movie.thumbnail
        )


        holder.viewBind.root.setOnClickListener {
            navController.navigate(R.id.movieDetailFrag, bundle)
            Log.d(TAG, "${bundle}")
        }

        holder.bindData(movie)
    }

    /*
    *   TODO: Complete the onBindViewHolder logic.
    * */
    override fun getItemCount(): Int {
        return data.size
    }
}