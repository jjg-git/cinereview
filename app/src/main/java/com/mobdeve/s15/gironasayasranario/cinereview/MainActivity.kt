package com.mobdeve.s15.gironasayasranario.cinereview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mobdeve.s15.gironasayasranario.cinereview.databinding.ActivityMainBinding
import com.mobdeve.s15.gironasayasranario.cineview.Movie

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private lateinit var data: ArrayList<Movie>
    private lateinit var movieRecycler: RecyclerView
    private lateinit var movieLayout: LinearLayoutManager

    private val movieLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        movieRecycler = binding.movieRv

        this.data = DataGenerator.generateData()
        this.movieLayout = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)

        movieRecycler.layoutManager = movieLayout
        movieRecycler.adapter = MovieAdapter(data, movieLauncher)



    }
}