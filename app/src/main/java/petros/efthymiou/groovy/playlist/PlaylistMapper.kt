package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.vo.Playlist
import petros.efthymiou.groovy.vo.PlaylistRaw
import javax.inject.Inject

class PlaylistMapper @Inject constructor(): Function1<List<PlaylistRaw>, List<Playlist>> {
    override fun invoke(p1: List<PlaylistRaw>): List<Playlist> {
        TODO("Not yet implemented")
    }
}