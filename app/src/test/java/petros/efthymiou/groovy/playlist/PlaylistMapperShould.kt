package petros.efthymiou.groovy.playlist

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.vo.Playlist
import petros.efthymiou.groovy.vo.PlaylistRaw
import petros.efthymiou.groovy.R

class PlaylistMapperShould: BaseUnitTest() {

    private lateinit var mapper: PlaylistMapper
    private lateinit var playlistRaw: PlaylistRaw
    private lateinit var playlistRawRock: PlaylistRaw
    private lateinit var playlists: List<Playlist>
    private lateinit var playlist: Playlist
    private lateinit var playlistRock: Playlist

    @Before
    fun setup(){
        playlistRaw = PlaylistRaw("1", "name", "category")
        playlistRawRock = PlaylistRaw("1", "name", "rock")

        mapper = PlaylistMapper()

        playlists = mapper(listOf(playlistRaw))
        playlist = playlists[0]
        playlistRock = mapper(listOf(playlistRawRock))[0]

    }

    @Test
    fun keepSameId(){
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName(){
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory(){
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock(){
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory(){
        assertEquals(R.mipmap.rock, playlistRock.image)
    }

}