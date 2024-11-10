package co.edu.uniandes.misw4203.proyectovinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.edu.uniandes.misw4203.proyectovinilos.databinding.CollectorItemBinding
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector
import com.bumptech.glide.Glide

class CollectorsAdapter(private val onCollectorClick: (Collector) -> Unit) : RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>() {

    var collectors: List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false)
        return CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collector = collectors[position]
            // Load profile picture using Glide
            Glide.with(holder.viewDataBinding.root.context)
                .load(collectors[position].name)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(holder.viewDataBinding.albumCover)

            holder.viewDataBinding.executePendingBindings()
            holder.itemView.setOnClickListener {
                onCollectorClick(collectors[position])
            }
        }
    }

    override fun getItemCount(): Int = collectors.size

    class CollectorViewHolder(val viewDataBinding: CollectorItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }
}