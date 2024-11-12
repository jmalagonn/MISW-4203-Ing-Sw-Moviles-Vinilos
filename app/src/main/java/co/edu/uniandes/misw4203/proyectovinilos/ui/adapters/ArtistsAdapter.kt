package co.edu.uniandes.misw4203.proyectovinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.ArtistItemBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import com.bumptech.glide.Glide

class ArtistsAdapter(private val onArtistClick: (Artist) -> Unit) : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {
    var artists :List<Artist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false)
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artist = artists[position]
            // Load covers using Glide Lib
            Glide.with(holder.viewDataBinding.root.context)
                .load(artists[position].image)
                .placeholder(android.R.drawable.ic_menu_camera)
                .error(android.R.drawable.ic_menu_gallery)
                .into(holder.viewDataBinding.artistImage)

            holder.viewDataBinding.executePendingBindings()
            holder.itemView.setOnClickListener {
                onArtistClick(artists[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    class ArtistViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }
    }
}

