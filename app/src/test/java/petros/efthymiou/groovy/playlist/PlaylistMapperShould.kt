package petros.efthymiou.groovy.playlist

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.vo.Playlist
import petros.efthymiou.groovy.vo.PlaylistRaw

class PlaylistMapperShould: BaseUnitTest() {

    private lateinit var mapper: PlaylistMapper
    private lateinit var playlistRaw: PlaylistRaw
    private lateinit var playlists: List<Playlist>
    private lateinit var playlist: Playlist

    @Before
    fun setup(){
        playlistRaw = PlaylistRaw("1", "name", "category")
        mapper = PlaylistMapper()
        playlists = mapper(listOf(playlistRaw))
        playlist = playlists[0]

    }

    @Test
    fun keepSameId(){
        assertEquals(playlistRaw.id, playlist.id)
    }

}