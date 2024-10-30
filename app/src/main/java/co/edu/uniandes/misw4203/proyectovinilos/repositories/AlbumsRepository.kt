package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class AlbumsRepository (private val application: Application){

    /**
     * Get the Albums list
     */
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getAlbums({
            //Guardar los albumes de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }

    /**
     * Get Album by id
     */
    fun getAlbumDetail(albumId: Int, callback: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbumDetail(albumId, {
            callback(it)
        }, onError)
    }
}