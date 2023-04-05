package tdd.app.musicapp.repositories

import kotlinx.coroutines.flow.Flow
import tdd.app.musicapp.apiservices.PlaylistService
import tdd.app.musicapp.models.PlaylistDetailData
import javax.inject.Inject

class PlaylistDetailRepo @Inject constructor(val playlistService: PlaylistService) {

    suspend fun getPlaylistDetailById(id: String): Flow<Result<PlaylistDetailData>> {
        return playlistService.fetchPlaylistDetailById(id)
    }

}
