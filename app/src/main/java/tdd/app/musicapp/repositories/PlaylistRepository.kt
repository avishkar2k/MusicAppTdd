package tdd.app.musicapp.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import tdd.app.musicapp.apiservice.PlaylistService
import tdd.app.musicapp.models.Playlist
import tdd.app.musicapp.models.PlaylistMapper
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val playlistService: PlaylistService,
    private val playlistMapper: PlaylistMapper
) {

    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> {
        return playlistService.fetchPlaylists().map {
            if (it.isSuccess) {
                Result.success<List<Playlist>>(playlistMapper(it.getOrNull()!!))
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

}
