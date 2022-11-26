package tdd.app.musicapp.apiservice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import tdd.app.musicapp.models.Playlist
import tdd.app.musicapp.models.PlaylistApiResponse
import javax.inject.Inject

class PlaylistService @Inject constructor(
    private val playlistApi: PlaylistApi
) {
    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistApiResponse>>> {
        return flow {
            emit(Result.success(playlistApi.fetchAllPlaylist()))
        }.catch {
            emit(Result.failure<List<PlaylistApiResponse>>(RuntimeException("Something went wrong")))
        }
    }

}
