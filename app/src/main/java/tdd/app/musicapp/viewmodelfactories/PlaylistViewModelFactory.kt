package tdd.app.musicapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tdd.app.musicapp.repositories.PlaylistRepository
import tdd.app.musicapp.viewmodels.PlaylistViewModel
import javax.inject.Inject

class PlaylistViewModelFactory @Inject constructor(private val playlistRepository: PlaylistRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PlaylistViewModel(playlistRepository) as T
    }
}
