package tdd.app.musicapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import tdd.app.musicapp.databinding.PlaylistItemBinding
import tdd.app.musicapp.models.PlaylistData

/**
 * [RecyclerView.Adapter] that can display a [PlaylistData].
 */
class MyPlaylistRecyclerViewAdapter(
    private val values: List<PlaylistData>,
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PlaylistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvPlaylistName.text = item.name
        holder.tvPlaylistCategory.text = item.category
        holder.ivPlaylistIcon.setImageResource(item.image)
        holder.root.setOnClickListener { clickListener(item.id) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: PlaylistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvPlaylistName: TextView = binding.tvPlaylistName
        val tvPlaylistCategory: TextView = binding.tvPlaylistCategory
        val ivPlaylistIcon: ImageView = binding.ivPlaylistIcon
        val root: ConstraintLayout = binding.playlistItemRoot
    }

}