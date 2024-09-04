package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.vo.Playlist

class PlaylistRepository(private val service: PlaylistService) {

    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> = service.fetchPlaylists()

}
