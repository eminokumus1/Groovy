package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.vo.Playlist
import petros.efthymiou.groovy.vo.PlaylistRaw
import javax.inject.Inject

class PlaylistMapper @Inject constructor(): Function1<List<PlaylistRaw>, List<Playlist>> {
    override fun invoke(playlistsRaw: List<PlaylistRaw>): List<Playlist> {
        return playlistsRaw.map {
            val image = when(it.category){
                "rock" -> R.mipmap.rock
                else -> R.mipmap.playlist
            }
            Playlist(it.id, it.name, it.category, image)
        }
    }
}