package co.edu.uniandes.misw4203.proyectovinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.edu.uniandes.misw4203.proyectovinilos.database.VinylRoomDatabase
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.repositories.ArtistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val _artists = MutableLiveData<List<Artist>>()
    private val _artistsRepository = ArtistsRepository(application,
        VinylRoomDatabase.getDatabase(application.applicationContext).artistsDao())

    val artists: LiveData<List<Artist>>
        get() = _artists

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
                    val data = _artistsRepository.refreshData()
                    _artists.postValue(data)
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
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}