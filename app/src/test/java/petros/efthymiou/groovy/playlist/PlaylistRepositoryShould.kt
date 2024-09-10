package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.vo.Playlist
import petros.efthymiou.groovy.vo.PlaylistRaw
import java.lang.RuntimeException

class PlaylistRepositoryShould : BaseUnitTest() {

    private lateinit var repository: PlaylistRepository
    private lateinit var service: PlaylistService
    private lateinit var mapper: PlaylistMapper
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")

    @Before
    fun setup() {
        service = mock()
        mapper = mock()
        repository = PlaylistRepository(service, mapper)
    }

    @Test
    fun getPlaylistsFromService(): Unit = runBlocking {
        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitMappedPlaylistFromService(): Unit = runBlocking {
        repository = mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun propagateErrors(): Unit = runBlocking {
        repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper(): Unit = runBlocking {
        repository = mockSuccessfulCase()

        repository.getPlaylists().first()

        verify(mapper, times(1)).invoke(playlistsRaw)

    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<PlaylistRaw>>(exception))
            }
        )
        repository = PlaylistRepository(service, mapper)
        return repository
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlistsRaw))
            }
        )

        whenever(mapper.invoke(playlistsRaw)).thenReturn(playlists)

        repository = PlaylistRepository(service, mapper)

        return repository
    }
}