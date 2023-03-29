package tdd.app.musicapp.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import tdd.app.musicapp.apiservices.PlaylistApi
import tdd.app.musicapp.apiservices.PlaylistService
import tdd.app.musicapp.models.PlaylistApiResponseData
import tdd.app.musicapp.util.BaseUnitTest

class PlaylistServiceShould : BaseUnitTest() {


    private lateinit var playlistService: PlaylistService
    private val playlists: List<PlaylistApiResponseData> = mock()
    private val playlistApi: PlaylistApi = mock()

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

    private suspend fun mockSuccessfulCase() {
        whenever(playlistApi.fetchAllPlaylist()).thenReturn(playlists)
        playlistService = PlaylistService(playlistApi)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorResultWhenNetworkFails(): Unit = runTest {
        mockFailureCase()

        assertEquals(
            "Something went wrong",
            playlistService.fetchPlaylists().first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockFailureCase() {
        whenever(playlistApi.fetchAllPlaylist()).thenThrow(RuntimeException("Something went wrong"))
        playlistService = PlaylistService(playlistApi)
    }
}