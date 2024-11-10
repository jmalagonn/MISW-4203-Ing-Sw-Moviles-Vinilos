package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import android.util.Log
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter

class AlbumsRepository(private val application: Application) {
    suspend fun refreshData(): List<Album> {
        // Cache handling
        val cacheManager = CacheManager.getInstance(application.applicationContext)

        return try {
            // Get albums from API
            val albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            if (albums.isNotEmpty()) {
                // Update albums in cache
                cacheManager.addAlbums(albums)
                Log.d("Cache decision", "Data fetched from network and cached")
                albums
            } else {
                // Get albums from cache
                Log.d("Cache decision", "No albums from network, returning cached data")
                cacheManager.getAlbums()
            }
        } catch (e: Exception) {
            // Get albums from cache
            Log.e("Network error", "Error fetching data from network, returning cached data", e)
            cacheManager.getAlbums()
        }
    }

}