package petros.efthymiou.groovy.playlist

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.vo.Playlist
import petros.efthymiou.groovy.vo.PlaylistRaw
import javax.inject.Inject

class PlaylistService @Inject constructor(private val api: PlaylistAPI) {

    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistRaw>>> {
        val flow = api.fetchAllPlaylists() ?: null
        Log.i("api values", flow.toString())
        return flow {
            emit(Result.success(api.fetchAllPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
