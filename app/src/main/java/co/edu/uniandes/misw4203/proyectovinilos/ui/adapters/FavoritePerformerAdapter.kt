package co.edu.uniandes.misw4203.proyectovinilos.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.models.Performer

class FavoritePerformerAdapter(private val favoritePerformers: List<Performer>): RecyclerView.Adapter<FavoritePerformerAdapter.FavoritePerformerAdapterViewHolder>() {

    class FavoritePerformerAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val counter: TextView = itemView.findViewById(R.id.favorite_performer_counter)
        val title: TextView = itemView.findViewById(R.id.favorite_performer_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePerformerAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_performer_item, parent, false)
        return FavoritePerformerAdapterViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoritePerformerAdapterViewHolder, position: Int) {
        val favoritePerformer = favoritePerformers[position]
        holder.counter.text = (position + 1).toString()
        holder.title.text = favoritePerformer.name
    }

    override fun getItemCount(): Int {
        return favoritePerformers.size
    }

}