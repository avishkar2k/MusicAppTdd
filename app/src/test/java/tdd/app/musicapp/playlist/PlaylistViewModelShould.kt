package tdd.app.musicapp.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.times
import tdd.app.musicapp.models.PlaylistData
import tdd.app.musicapp.repositories.PlaylistRepository
import tdd.app.musicapp.viewmodels.PlaylistViewModel
import tdd.app.musicapp.util.BaseUnitTest
import tdd.app.musicapp.util.captureValues
import tdd.app.musicapp.util.getValueForTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class PlaylistViewModelShould : BaseUnitTest() {

    private val exception = RuntimeException("Something went wrong")
    private val playlistRepository: PlaylistRepository = mock()

    private val playlists: List<PlaylistData> = mock()
    private val expected = Result.success(playlists)

    @Test
    fun getPlaylistFromRepository(): Unit = runTest {
        val playlistViewModel = mockSuccessfulCase()
        playlistViewModel.playlists.getValueForTest()
        verify(playlistRepository, times(1)).getPlaylists()
    }

    @Test
    fun emitResultFromRepository(): Unit = runTest {
        val playlistViewModel = mockSuccessfulCase()
        assertEquals(expected, playlistViewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorFromRepositoryFailureResult() {
        val playlistViewModel = mockFailureCase()
        assertEquals(exception, playlistViewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showLoaderWhileFetchingPlaylist():Unit = runTest {
        val playlistViewModel = mockSuccessfulCase()
        playlistViewModel.loader.captureValues{
            playlistViewModel.playlists.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderWhenPlaylistsApiRequestIsSuccessful():Unit = runTest{
        val playlistViewModel = mockSuccessfulCase()
        playlistViewModel.loader.captureValues{
            playlistViewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

  @Test
    fun hideLoaderWhenPlaylistsApiRequestIsFails():Unit = runTest{
        val playlistViewModel = mockFailureCase()
        playlistViewModel.loader.captureValues{
            playlistViewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }



    private fun mockSuccessfulCase(): PlaylistViewModel {
        runBlocking {
            whenever(playlistRepository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        return PlaylistViewModel(playlistRepository)
    }

    private fun mockFailureCase(): PlaylistViewModel {
        runBlocking {
            whenever(playlistRepository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<PlaylistData>>(exception))
                }
            )
        }

        return PlaylistViewModel(playlistRepository)
    }

}