package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.vo.Playlist
import petros.efthymiou.groovy.vo.PlaylistRaw
import java.lang.RuntimeException

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private lateinit var api: PlaylistAPI
    private val playlistsRaw = mock<List<PlaylistRaw>>()

    @Before
    fun setup() {
        api = mock()
        service = PlaylistService(api)
    }

    @Test
    fun fetchPlaylistsFromAPI(): Unit = runBlocking() {
        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()

    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem(): Unit = runBlocking {
        mockSuccessfulCase()

        assertEquals(Result.success(playlistsRaw), service.fetchPlaylists().first())
    }



    @Test
    fun emitErrorResultWhenNetworkFails(): Unit = runBlocking {
        mockErrorCase()

        assertEquals("Something went wrong", service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Something went wrong"))

        service = PlaylistService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists()).thenReturn(playlistsRaw)

        service = PlaylistService(api)
    }
}