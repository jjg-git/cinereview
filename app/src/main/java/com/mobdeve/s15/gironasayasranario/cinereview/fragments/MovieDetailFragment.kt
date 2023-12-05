package com.mobdeve.s15.gironasayasranario.cinereview.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobdeve.s15.gironasayasranario.cinereview.R
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.FragmentMovieDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val ARG_NAME = "name"
private const val ARG_DESCRIPTION = "description"
private const val ARG_CAST = "cast"
private const val ARG_DIRECTOR = "director"
private const val ARG_RATING = "rating"
private const val ARG_POSTER = "poster"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var name: String? = null
    private var description: String? = null
    private var cast: String? = null
    private var director: String? = null
    private var rating: String? = null
    private var poster: Int? = null

    private val TAG = "MovieDetailFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            description = it.getString("description")
            director = it.getString("director")
            cast = it.getString("cast")
            rating = it.getString("rating")
            poster = it.getInt("poster")

            Log.d(TAG, "$it")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMovieDetailBinding.inflate(inflater)
        val view = binding.root

        binding.titleMovieTv.setText(name)
        binding.descriptionTv.setText(description)
        binding.castTv.setText(cast)
        binding.directorTv.setText(director)
        binding.moviePosterIv.setImageResource(poster!!)
        when (rating) {
            "G" -> binding.ratingIv.setImageResource(R.drawable.mtrcb_g)
            "PG" -> binding.ratingIv.setImageResource(R.drawable.mtrcb_pg)
            "SPG" -> binding.ratingIv.setImageResource(R.drawable.mtrcb_spg)
            else -> binding.ratingIv.setImageResource(R.drawable.mtrcb_g)
        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String, description: String,
                        cast: String, director: String, rating: String) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {

                    putString(ARG_NAME, name)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_CAST, cast)
                    putString(ARG_DIRECTOR, director)
                    putString(ARG_RATING, rating)
                }
            }
    }
}