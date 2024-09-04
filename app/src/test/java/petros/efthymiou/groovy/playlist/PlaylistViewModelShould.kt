package petros.efthymiou.groovy.playlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.junit.Rule
import petros.efthymiou.groovy.playlist.PlaylistRepository
import petros.efthymiou.groovy.playlist.PlaylistViewModel
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.MainCoroutineScopeRule
import petros.efthymiou.groovy.utils.getValueForTest
import petros.efthymiou.groovy.vo.Playlist
import java.lang.Exception
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

        viewModel = mockSuccesfulCase()
        viewModel.playlists.getValueForTest()


        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistFromRepository(): Unit = runBlocking {
        viewModel = mockSuccesfulCase()

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError(){
        viewModel = mockErrorCase()

        assertEquals(exception, viewModel.playlists.getValueForTest()?.exceptionOrNull() )
        
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


    private fun mockSuccesfulCase(): PlaylistViewModel {
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























