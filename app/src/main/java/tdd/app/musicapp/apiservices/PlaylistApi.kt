package tdd.app.musicapp.apiservices

import retrofit2.http.GET
import retrofit2.http.Path
import tdd.app.musicapp.models.PlaylistApiResponseData
import tdd.app.musicapp.models.PlaylistDetailData

interface PlaylistApi {

    @GET("playlists")
    suspend fun fetchAllPlaylist(): List<PlaylistApiResponseData>

    @GET("playlist-details/{id}")
    suspend fun fetchPlaylistDetailById(@Path("id") id: String): PlaylistDetailData

}
