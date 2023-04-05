package tdd.app.musicapp.playlistDetails

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
import tdd.app.musicapp.apiservices.PlaylistService
import tdd.app.musicapp.models.PlaylistDetailData
import tdd.app.musicapp.repositories.PlaylistDetailRepo
import tdd.app.musicapp.util.BaseUnitTest

/**
 * Playlist Detail Repo Should
 * 1. call the api from playlist service
 * 2. emit successful case
 * 3. emit failure case
 */
@ExperimentalCoroutinesApi
class PlaylistDetailRepoShould : BaseUnitTest() {

    private val id: String = "1"
    private lateinit var playlistDetailRepo: PlaylistDetailRepo
    private val playlistDetailData: PlaylistDetailData = mock()
    private val expected: Result<PlaylistDetailData> = Result.success(playlistDetailData)
    private val exception: RuntimeException = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetailData>(exception)
    private val playlistDetailService: PlaylistService = mock()

    @Test
    fun callFetchDetailApiFromService(): Unit = runTest {
        mockSuccessfulCase()
        verify(playlistDetailService, times(1)).fetchPlaylistDetailById(id)
    }

    @Test
    fun emitPlaylistDetailFromApiRequest(): Unit = runTest {
        mockSuccessfulCase()
        assertEquals(expected, playlistDetailRepo.getPlaylistDetailById(id).first())
    }

    @Test
    fun emitPlaylistDetailErrorFromApiRequest(): Unit = runTest {
        mockErrorCase()
        assertEquals(error, playlistDetailRepo.getPlaylistDetailById(id).first())
    }

    private suspend fun mockErrorCase() {
        whenever(playlistDetailService.fetchPlaylistDetailById(id)).thenReturn(
            flow { emit(error) }
        )
        makeApiCallForPlaylistDetail()
    }

    private suspend fun mockSuccessfulCase() {
        whenever(playlistDetailService.fetchPlaylistDetailById(id)).thenReturn(
            flow { emit(expected) }
        )
        makeApiCallForPlaylistDetail()
    }

    private suspend fun makeApiCallForPlaylistDetail() {
        playlistDetailRepo = PlaylistDetailRepo(playlistDetailService)
        playlistDetailRepo.getPlaylistDetailById(id)
    }
}