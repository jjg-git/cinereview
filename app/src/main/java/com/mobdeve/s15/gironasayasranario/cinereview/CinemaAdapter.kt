import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobdeve.s15.gironasayasranario.cinereview.Cinema
import com.mobdeve.s15.gironasayasranario.cinereview.R

class CinemaAdapter(var cinemas: List<Cinema>) : RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder>() {

    class CinemaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.cinemaImageView)
        private val textView: TextView = view.findViewById(R.id.cinemaNameTextView)
        private val distanceTextView: TextView = view.findViewById(R.id.cinemaDistanceTextView)

        fun bind(cinema: Cinema) {

            Glide.with(itemView.context)
                .load(cinema.imageUrl)
                .into(imageView)

            textView.text = cinema.name
            distanceTextView.text = "${String.format("%.2f", cinema.distanceToUser)} km away"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cinema, parent, false)
        return CinemaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        holder.bind(cinemas[position])
    }

    override fun getItemCount() = cinemas.size

    // Method to update the list of cinemas
    fun updateCinemas(newCinemas: List<Cinema>) {
        cinemas = newCinemas
        notifyDataSetChanged()
    }

}
