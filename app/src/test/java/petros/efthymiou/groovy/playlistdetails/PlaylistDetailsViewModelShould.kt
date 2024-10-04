package petros.efthymiou.groovy.playlistdetails

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import petros.efthymiou.groovy.vo.PlaylistDetails
import java.lang.RuntimeException

class PlaylistDetailsViewModelShould: BaseUnitTest(){

    private lateinit var viewModel: PlaylistDetailsViewModel
    private lateinit var service: PlaylistDetailsService
    private val id = "1"
    private val playlistDetails = mock<PlaylistDetails>()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")

    @Before
    fun setup(){
        service = mock()
        viewModel = PlaylistDetailsViewModel(service)
    }

    @Test
    fun getPlaylistDetailsFromService(): Unit = runBlocking {
        viewModel.getPlaylistDetails(id)

        viewModel.playlistDetails.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailsFromService(): Unit = runBlocking {
        viewModel = mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails(): Unit = runBlocking {
        viewModel = mockFailureCase()

        viewModel.getPlaylistDetails(id)

        assertEquals(Result.failure<PlaylistDetails>(exception), viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showLoaderWhileLoading(): Unit = runBlocking {
        viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailsLoad(): Unit = runBlocking {
        viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)

            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailsViewModel {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(Result.success(playlistDetails))
            }
        )
        viewModel = PlaylistDetailsViewModel(service)

        return viewModel
    }

    private suspend fun mockFailureCase(): PlaylistDetailsViewModel {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(Result.failure<PlaylistDetails>(exception))
            }
        )
        viewModel = PlaylistDetailsViewModel(service)

        return viewModel
    }


}