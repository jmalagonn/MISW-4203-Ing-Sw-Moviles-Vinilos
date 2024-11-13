package co.edu.uniandes.misw4203.proyectovinilos.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.models.Album

class ArtistAlbumAdapter(private val albums: List<Album>) : RecyclerView.Adapter<ArtistAlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val counter: TextView = itemView.findViewById(R.id.album_counter)
        val title: TextView = itemView.findViewById(R.id.album_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_album_item, parent, false)
        return AlbumViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.counter.text = (position + 1).toString() // Muestra el número de álbum como contador
        holder.title.text = album.name // Muestra el nombre del álbum
    }

    override fun getItemCount(): Int = albums.size
}
