package tdd.app.musicapp.apiservice

import retrofit2.http.GET
import tdd.app.musicapp.models.PlaylistApiResponse

interface PlaylistApi {

    @GET("playlists")
    suspend fun fetchAllPlaylist(): List<PlaylistApiResponse>

}
