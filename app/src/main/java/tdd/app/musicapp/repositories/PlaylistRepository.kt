package tdd.app.musicapp.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tdd.app.musicapp.apiservices.PlaylistService
import tdd.app.musicapp.models.PlaylistData
import tdd.app.musicapp.models.PlaylistMapper
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val playlistService: PlaylistService,
    private val playlistMapper: PlaylistMapper
) {

    suspend fun getPlaylists(): Flow<Result<List<PlaylistData>>> {
        return playlistService.fetchPlaylists().map {
            if (it.isSuccess) {
                Result.success(playlistMapper(it.getOrNull()!!))
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    }

}
