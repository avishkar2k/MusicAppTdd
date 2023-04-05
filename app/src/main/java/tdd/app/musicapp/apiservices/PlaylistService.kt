package tdd.app.musicapp.apiservices

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import tdd.app.musicapp.models.PlaylistApiResponseData
import tdd.app.musicapp.models.PlaylistDetailData
import javax.inject.Inject

class PlaylistService @Inject constructor(
    private val playlistApi: PlaylistApi
) {

    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistApiResponseData>>> {
        return flow {
            emit(Result.success(playlistApi.fetchAllPlaylist()))
        }.catch {
            emit(Result.failure<List<PlaylistApiResponseData>>(RuntimeException("Something went wrong")))
        }
    }

    suspend fun fetchPlaylistDetailById(id: String): Flow<Result<PlaylistDetailData>> {
        return flow {
            emit(Result.success(playlistApi.fetchPlaylistDetailById(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }


}
