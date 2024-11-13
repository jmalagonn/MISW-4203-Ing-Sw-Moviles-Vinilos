package co.edu.uniandes.misw4203.proyectovinilos.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.models.Track

class TrackAdapter(private val tracks: List<Track>) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val counter: TextView = itemView.findViewById(R.id.track_counter)
        val title: TextView = itemView.findViewById(R.id.track_title)
        val duration: TextView = itemView.findViewById(R.id.track_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        return TrackViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.counter.text = (position + 1).toString()
        holder.title.text = track.name
        holder.duration.text = track.duration
    }

    override fun getItemCount(): Int = tracks.size
}
