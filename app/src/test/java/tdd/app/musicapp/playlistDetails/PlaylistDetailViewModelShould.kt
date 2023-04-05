package tdd.app.musicapp.playlistDetails

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Test
import tdd.app.musicapp.models.PlaylistDetailData
import tdd.app.musicapp.repositories.PlaylistDetailRepo
import tdd.app.musicapp.util.BaseUnitTest
import tdd.app.musicapp.util.captureValues
import tdd.app.musicapp.util.getValueForTest
import tdd.app.musicapp.viewmodels.PlaylistDetailsViewModel

/**
 * Playlist Detail View Model Should:
 * 1. Handle the status for showing loader
 * 2. Handle the status for hiding loader
 * 3. Should call the service to fetch the playlist detail from repository
 * 4. Should emit the playlist detail data from the repository
 * 5. Should emit the exception from playlist details repository
 */
@ExperimentalCoroutinesApi
class PlaylistDetailViewModelShould : BaseUnitTest() {
    private lateinit var playlistDetailsViewModel: PlaylistDetailsViewModel
    private val playlistDetailRepo: PlaylistDetailRepo = mock()
    private val playlistDetailData: PlaylistDetailData = mock()
    private val expected = Result.success(playlistDetailData)
    private val exception = RuntimeException("Something went wrong!")
    private val error = Result.failure<PlaylistDetailData>(exception)
    private val id: String = "1"

    @Test
    fun handleStatusShowLoaderWhenFetchingRequest(): Unit = runTest {
        makeApiCallForPlaylistDetail()
        playlistDetailsViewModel.loader.captureValues {
            assertEquals(true, values[0])
        }
    }

    @Test
    fun handleStatusHideLoaderWhenFetchingRequestCompletes(): Unit = runTest {
        mockSuccessfulCase()
        playlistDetailsViewModel.loader.captureValues {
            assertEquals(false, values.last())
        }
    }

    @Test
    fun getPlaylistDetailFromRepository(): Unit = runTest {
        mockSuccessfulCase()
        verify(playlistDetailRepo, times(1)).getPlaylistDetailById(id)
    }

    @Test
    fun emitPlaylistDetailDataFromRepository(): Unit = runTest {
        mockSuccessfulCase()
        assertEquals(
            expected,
            playlistDetailsViewModel.playlistDetailDataLiveData.getValueForTest()
        )
    }

    @Test
    fun emitPlaylistDetailErrorFromRepository(): Unit = runTest {
        mockErrorCase()
        assertEquals(
            error,
            playlistDetailsViewModel.playlistDetailDataLiveData.getValueForTest()
        )
    }

    private suspend fun mockSuccessfulCase() {
        whenever(playlistDetailRepo.getPlaylistDetailById(id)).thenReturn(
            flow { emit(expected) }
        )
        makeApiCallForPlaylistDetail()
    }

    private suspend fun mockErrorCase() {
        whenever(playlistDetailRepo.getPlaylistDetailById(id)).thenReturn(
            flow { emit(error) }
        )
        makeApiCallForPlaylistDetail()
    }

    private fun makeApiCallForPlaylistDetail() {
        playlistDetailsViewModel = PlaylistDetailsViewModel(playlistDetailRepo)
        playlistDetailsViewModel.getPlaylistDetailById(id)
    }
}