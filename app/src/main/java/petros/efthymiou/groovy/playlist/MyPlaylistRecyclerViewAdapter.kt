package petros.efthymiou.groovy.playlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.vo.Playlist

import petros.efthymiou.groovy.databinding.PlaylistItemBinding


class MyPlaylistRecyclerViewAdapter(
) : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

    var values: List<Playlist> = listOf()

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
        holder.bind(item)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: PlaylistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist){
            binding.run{
                playlistName.text = playlist.name
                playlistCategory.text = playlist.category
                playlistImage.setImageResource(R.mipmap.playlist)
            }
        }
    }

}