package com.mobdeve.s15.gironasayasranario.cinereview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mobdeve.s15.gironasayasranario.cineview.Movie

class ShowingTimeListAdapter(data: ArrayList<Movie>): RecyclerView.Adapter<ShowingTimeViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowingTimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.showing_time_item, parent, false)
        val view: View = inflater


        return ShowingTimeViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ShowingTimeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

class ShowingTimeViewHolder(itemView: View) : ViewHolder(itemView) {
    lateinit var statusIV: ImageView
    lateinit var timeTv: TextView

    fun bindData() {

    }
}