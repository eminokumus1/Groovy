package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import petros.efthymiou.groovy.vo.Playlist

class PlaylistRepository {
    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> {
        TODO("Not yet implemented")
    }

}
