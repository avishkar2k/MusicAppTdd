package tdd.app.musicapp.models

import androidx.annotation.DrawableRes

data class PlaylistData(
    val name: String,
    val category: String,
    val id: String,
    @DrawableRes
    val image: Int
)