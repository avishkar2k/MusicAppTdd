package tdd.app.musicapp.models

import tdd.app.musicapp.R
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistApiResponse>, List<Playlist>> {

    override fun invoke(playlistApiResponse: List<PlaylistApiResponse>): List<Playlist> {
        return playlistApiResponse.map {

            val image = when (it.category) {
                "rock" -> R.mipmap.rock
                else -> R.mipmap.playlist
            }

            Playlist(
                it.name, it.category, it.id, image
            )
        }
    }

}
