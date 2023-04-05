package tdd.app.musicapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.onEach
import tdd.app.musicapp.models.PlaylistData
import tdd.app.musicapp.repositories.PlaylistRepository

class PlaylistViewModel(private val playlistRepository: PlaylistRepository) : ViewModel() {

    val loader: MutableLiveData<Boolean> = MutableLiveData()
    val playlists = liveData<Result<List<PlaylistData>>> {
        loader.postValue(true)
        emitSource(playlistRepository.getPlaylists()
            .onEach {
                loader.postValue(false)
            }.asLiveData()
        )
    }
}
