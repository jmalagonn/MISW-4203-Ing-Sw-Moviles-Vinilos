package co.edu.uniandes.misw4203.proyectovinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import co.edu.uniandes.misw4203.proyectovinilos.database.VinylRoomDatabase
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
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

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

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