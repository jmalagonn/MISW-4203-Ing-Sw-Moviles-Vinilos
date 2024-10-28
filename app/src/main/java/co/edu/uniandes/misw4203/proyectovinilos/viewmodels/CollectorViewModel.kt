package co.edu.uniandes.misw4203.proyectovinilos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollectorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Collector Fragment"
    }
    val text: LiveData<String> = _text
}