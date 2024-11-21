package co.edu.uniandes.misw4203.proyectovinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.edu.uniandes.misw4203.proyectovinilos.database.VinylRoomDatabase
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Track
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter
import co.edu.uniandes.misw4203.proyectovinilos.repositories.AlbumsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(application: Application) :  AndroidViewModel(application) {

    private val _albums = MutableLiveData<List<Album>>()

    private val albumsRepository = AlbumsRepository(application,
        VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao())

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application.applicationContext)

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default){
                withContext(Dispatchers.IO){
                    val data = albumsRepository.refreshData()
                    _albums.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun createAlbum(album: Album, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val applicationContext = getApplication<Application>()
            NetworkServiceAdapter.getInstance(applicationContext).createAlbum(
                album = album,
                onSuccess = {
                    onSuccess()
                },
                onError = { errorMessage ->
                    onError(errorMessage)
                }
            )
        }
    }

    fun addTrackToAlbum(albumId: Int, track: Track, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                networkServiceAdapter.addTrackToAlbum(
                    albumId = albumId,
                    track = track,
                    onSuccess = {
                        onSuccess()
                        refreshDataFromNetwork()
                    },
                    onError = { errorMessage ->
                        onError(errorMessage)
                    }
                )
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error occurred")
            }
        }
    }




    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}