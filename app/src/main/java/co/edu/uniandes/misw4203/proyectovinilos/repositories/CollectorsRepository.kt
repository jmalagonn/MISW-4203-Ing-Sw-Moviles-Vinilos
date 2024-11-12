package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector
import co.edu.uniandes.misw4203.proyectovinilos.network.CollectorNetworkServiceAdapter
import com.android.volley.VolleyError

class CollectorsRepository (private val application: Application){
    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        CollectorNetworkServiceAdapter.getInstance(application).getCollectors({
            callback(it)
        },
            onError
        )
    }
}
