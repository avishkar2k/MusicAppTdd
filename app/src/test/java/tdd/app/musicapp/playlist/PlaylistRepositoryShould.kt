package tdd.app.musicapp.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import tdd.app.musicapp.apiservice.PlaylistService
import tdd.app.musicapp.models.Playlist
import tdd.app.musicapp.models.PlaylistApiResponse
import tdd.app.musicapp.models.PlaylistMapper
import tdd.app.musicapp.repositories.PlaylistRepository
import tdd.app.musicapp.utils.BaseUnitTest

@ExperimentalCoroutinesApi
class PlaylistRepositoryShould : BaseUnitTest() {

    private val playlistMapper: PlaylistMapper = mock()
    private val exception: Throwable= RuntimeException("Something went wrong")
    private val playlistService: PlaylistService = mock()
    private val playlists: List<Playlist> = mock()
    private val playlistApiResponse: List<PlaylistApiResponse> = mock()


    @Test
    fun getPlaylistFromService(): Unit = runTest {
        val playlistRepository = mockSuccessfulCase()
        playlistRepository.getPlaylists()
        verify(playlistService, times(1)).fetchPlaylists()
    }

    @Test
    fun emitMappedPlaylistsFromService(): Unit = runTest {
        val playlistRepository = mockSuccessfulCase()

        assertEquals(playlists, playlistRepository.getPlaylists().first().getOrNull())

    }

    @Test
    fun emitErrorOnFailureFromServiceRequest():Unit = runTest{
        val playlistRepository = mockFailureCase()

        assertEquals(exception, playlistRepository.getPlaylists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper():Unit = runTest{
        val playlistRepository = mockSuccessfulCase()
        playlistRepository.getPlaylists().first()
        verify(playlistMapper, times(1)).invoke(playlistApiResponse)
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(playlistService.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        return PlaylistRepository(playlistService, playlistMapper)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(playlistService.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlistApiResponse))
            }
        )
        whenever(playlistMapper.invoke(playlistApiResponse)).thenReturn(playlists)
        return PlaylistRepository(playlistService, playlistMapper)
    }
}