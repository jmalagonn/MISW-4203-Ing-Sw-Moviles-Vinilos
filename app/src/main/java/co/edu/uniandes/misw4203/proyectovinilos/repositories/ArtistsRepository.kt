package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class ArtistsRepository(private val application: Application) {
    fun refreshData(callback: (List<Artist>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getArtists({
            callback(it)
        },
            onError
        )
    }
}