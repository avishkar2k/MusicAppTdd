package tdd.app.musicapp.repositories

import retrofit2.Retrofit
import tdd.app.musicapp.models.PlaylistDetailData
import javax.inject.Inject

class PlaylistDetailsRepo @Inject constructor(val retrofit: Retrofit) {
    fun getPlaylistDetailById(id: String): PlaylistDetailData {
        TODO("Not yet implemented")
    }

}
