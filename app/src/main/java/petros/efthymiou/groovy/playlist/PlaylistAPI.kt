package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.vo.Playlist
import retrofit2.http.GET

interface PlaylistAPI {

    @GET("playlists")
    suspend fun fetchAllPlaylists(): List<Playlist>
}