package petros.efthymiou.groovy.playlistdetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.vo.PlaylistDetails
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor(private val api: PlaylistDetailsAPI) {

    suspend fun fetchPlaylistDetails(id: String): Flow<Result<PlaylistDetails>> {
        return flow {
            emit(Result.success(api.fetchPlaylistDetails(id)))
        }
    }


}
