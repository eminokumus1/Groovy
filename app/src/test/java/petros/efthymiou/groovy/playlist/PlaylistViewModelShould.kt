package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import petros.efthymiou.groovy.vo.Playlist
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PlaylistViewModelShould: BaseUnitTest() {



    private lateinit var viewModel: PlaylistViewModel
    private lateinit var repository: PlaylistRepository
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Before
    fun setup() {
        repository = mock()
        viewModel = PlaylistViewModel(repository)


    }



    @Test
    fun getPlaylistsFromRepository(): Unit = runBlocking {

        viewModel = mockSuccessfulCase()
        viewModel.playlists.getValueForTest()


        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistFromRepository(): Unit = runBlocking {
        viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError(){
        viewModel = mockErrorCase()

        assertEquals(exception, viewModel.playlists.getValueForTest()?.exceptionOrNull() )
        
    }

    @Test
    fun showSpinnerWhileLoading(): Unit = runBlocking {
        viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistsLoad(): Unit = runBlocking{
        viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError(): Unit = runBlocking{
        viewModel = mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private fun mockErrorCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }
        viewModel = PlaylistViewModel(repository)
        return viewModel
    }


    private fun mockSuccessfulCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        viewModel = PlaylistViewModel(repository)

        return viewModel
    }
}























