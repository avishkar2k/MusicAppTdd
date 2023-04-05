package tdd.app.musicapp.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tdd.app.musicapp.repositories.PlaylistDetailRepo
import tdd.app.musicapp.viewmodels.PlaylistDetailsViewModel
import javax.inject.Inject

class PlaylistDetailsViewModelFactory @Inject constructor(private val playlistDetailRepo: PlaylistDetailRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PlaylistDetailsViewModel(playlistDetailRepo) as T
    }
}
