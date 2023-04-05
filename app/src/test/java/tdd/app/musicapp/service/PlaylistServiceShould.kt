package tdd.app.musicapp.service

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
import tdd.app.musicapp.apiservices.PlaylistApi
import tdd.app.musicapp.apiservices.PlaylistService
import tdd.app.musicapp.models.PlaylistApiResponseData
import tdd.app.musicapp.models.PlaylistDetailData
import tdd.app.musicapp.util.BaseUnitTest

class PlaylistServiceShould : BaseUnitTest() {

    private val id = "1"
    private lateinit var playlistService: PlaylistService
    private val playlists: List<PlaylistApiResponseData> = mock()
    private val playlistApi: PlaylistApi = mock()
    private val playlistDetailData: PlaylistDetailData = mock()
    private val exception: RuntimeException = RuntimeException("Something went wrong")

    @ExperimentalCoroutinesApi
    @Test
    fun fetchPlaylistFromPlaylistAPI(): Unit = runTest {
        mockSuccessfulCase()
        playlistService.fetchPlaylists().first()
        verify(playlistApi, times(1)).fetchAllPlaylist()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun convertValuesToFlowResultAndEmit(): Unit = runTest {
        mockSuccessfulCase()
        assertEquals(
            Result.success(playlists),
            playlistService.fetchPlaylists().first()
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchPlaylistFromPlaylistDetailAPI(): Unit = runTest {
        mockSuccessfulCaseForPlaylistDetail()
        playlistService.fetchPlaylistDetailById(id).first()
        verify(playlistApi, times(1)).fetchPlaylistDetailById(id)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorResultWhenNetworkFails(): Unit = runTest {
        mockFailureCase()
        assertEquals(
            exception.message,
            playlistService.fetchPlaylists().first().exceptionOrNull()?.message
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorResultWhenNetworkFailsForPlaylistDetails(): Unit = runTest {
        mockFailureCaseForPlaylistDetail()
        assertEquals(
            exception.message,
            playlistService.fetchPlaylistDetailById(id).first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockSuccessfulCase() {
        whenever(playlistApi.fetchAllPlaylist()).thenReturn(playlists)
        playlistService = PlaylistService(playlistApi)
    }


    private suspend fun mockFailureCase() {
        whenever(playlistApi.fetchAllPlaylist()).thenThrow(exception)
        playlistService = PlaylistService(playlistApi)
    }

    private suspend fun mockSuccessfulCaseForPlaylistDetail() {
        whenever(playlistApi.fetchPlaylistDetailById(id)).thenReturn(playlistDetailData)
        playlistService = PlaylistService(playlistApi)
    }


    private suspend fun mockFailureCaseForPlaylistDetail() {
        whenever(playlistApi.fetchPlaylistDetailById(id)).thenThrow(exception)
        playlistService = PlaylistService(playlistApi)
    }
}