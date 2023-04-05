package tdd.app.musicapp.models

import tdd.app.musicapp.R
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistApiResponseData>, List<PlaylistData>> {

    override fun invoke(playlistApiResponse: List<PlaylistApiResponseData>): List<PlaylistData> {
        return playlistApiResponse.map {

            val image = when (it.category) {
                "rock" -> R.mipmap.rock
                else -> R.mipmap.playlist
            }

            PlaylistData(
                it.name, it.category, it.id, image
            )
        }
    }

}
