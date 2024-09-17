package petros.efthymiou.groovy.playlistdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import petros.efthymiou.groovy.vo.PlaylistDetails

class PlaylistDetailsViewModel(private val service: PlaylistDetailsService): ViewModel() {

    val playlistDetails = MutableLiveData<Result<PlaylistDetails>>()

    fun getPlaylistDetails(id: String){
        viewModelScope.launch {
            service.fetchPlaylistDetails(id)
                .collect {
                    playlistDetails.postValue(it)
                }

        }
    }
}