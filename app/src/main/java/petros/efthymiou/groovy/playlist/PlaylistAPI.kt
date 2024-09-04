package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.vo.Playlist

interface PlaylistAPI {

    suspend fun fetchAllPlaylists(): List<Playlist>{
        TODO()
    }
}