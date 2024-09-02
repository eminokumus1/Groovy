package petros.efthymiou.groovy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistViewModel: ViewModel(){

    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlist: LiveData<List<Playlist>> get() = _playlists

}