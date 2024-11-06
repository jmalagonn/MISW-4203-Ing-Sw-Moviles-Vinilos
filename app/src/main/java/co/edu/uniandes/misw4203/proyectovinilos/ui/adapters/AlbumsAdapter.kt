package co.edu.uniandes.misw4203.proyectovinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.AlbumItemBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class AlbumsAdapter(private val onAlbumClick: (Album) -> Unit) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
            // Load covers using Glide Lib
            Glide.with(holder.viewDataBinding.root.context)
                .load(albums[position].cover)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                //adding cache for images
                .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.viewDataBinding.albumCover)

            holder.viewDataBinding.executePendingBindings()
            holder.itemView.setOnClickListener {
                onAlbumClick(albums[position])
            }
        }

    }

    override fun getItemCount(): Int = albums.size


    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}
