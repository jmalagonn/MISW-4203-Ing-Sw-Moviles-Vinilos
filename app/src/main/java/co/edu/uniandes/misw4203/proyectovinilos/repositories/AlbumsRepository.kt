package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import android.util.Log
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter

class AlbumsRepository(private val application: Application) {

    suspend fun refreshData(): List<Album> {
        // Init cache manager
        val cacheManager = CacheManager.getInstance(application.applicationContext)

        return try {
            // Get info from API
            val albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            if (albums.isNotEmpty()) {
                //Update info in cache
                cacheManager.addAlbums(albums)
                Log.d("Cache decision", "Data fetched from network and cached")
                albums
            } else {
                // Get albums from cache
                Log.d("Cache decision", "No albums from network, returning cached data")
                cacheManager.getAlbums() ?: emptyList()
            }
        } catch (e: Exception) {
            // Get Albums from cache
            Log.e("Network error", "Error fetching data from network, returning cached data", e)
            cacheManager.getAlbums() ?: emptyList()
        }
    }
}