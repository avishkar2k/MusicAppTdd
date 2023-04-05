package tdd.app.musicapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tdd.app.musicapp.models.PlaylistDetailData
import tdd.app.musicapp.repositories.PlaylistDetailRepo
import javax.inject.Inject

class PlaylistDetailsViewModel @Inject constructor(private val playlistDetailRepo: PlaylistDetailRepo) : ViewModel() {

    /**
     * Variable to handle the state for showing and hiding the loader
     */
    val loader: MutableLiveData<Boolean> = MutableLiveData()

    val playlistDetailDataLiveData: MutableLiveData<Result<PlaylistDetailData>> = MutableLiveData()

    /**
     * Call the API service to fetch the play list item detail by id
     * @param id id of the playlist item to be requested
     */
    fun getPlaylistDetailById(id: String) {
        loader.postValue(true)
        viewModelScope.launch {
            playlistDetailRepo.getPlaylistDetailById(id).collect {
                playlistDetailDataLiveData.postValue(it)
                loader.postValue(false)
            }
        }
    }
}
