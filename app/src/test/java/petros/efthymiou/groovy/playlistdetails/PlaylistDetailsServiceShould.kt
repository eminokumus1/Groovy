package petros.efthymiou.groovy.playlistdetails

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.vo.PlaylistDetails
import java.lang.RuntimeException

class PlaylistDetailsServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistDetailsService
    private lateinit var api: PlaylistDetailsAPI
    private val id = "100"
    private val playlistDetails = mock<PlaylistDetails>()
    private val exception = RuntimeException("Damn")


    @Before
    fun setup() {
        api = mock()
        service = PlaylistDetailsService(api)
    }

    @Test
    fun fetchPlaylistDetailsFromAPI(): Unit = runBlocking() {
        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem(): Unit = runBlocking {
        whenever(api.fetchPlaylistDetails(id)).thenReturn(playlistDetails)

        service = PlaylistDetailsService(api)

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).single())

    }

    @Test
    fun emitErrorResultWhenNetworkFails(): Unit = runBlocking {
        whenever(api.fetchPlaylistDetails(id)).thenThrow(exception)

        service = PlaylistDetailsService(api)

        assertEquals("Something went wrong", service.fetchPlaylistDetails(id).single().exceptionOrNull()?.message)


    }


}