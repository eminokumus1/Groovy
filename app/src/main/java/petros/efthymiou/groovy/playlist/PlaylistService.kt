package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import petros.efthymiou.groovy.vo.Playlist

class PlaylistService {

    suspend fun fetchPlaylists(): Flow<Result<List<Playlist>>> {
        TODO()
    }

}
