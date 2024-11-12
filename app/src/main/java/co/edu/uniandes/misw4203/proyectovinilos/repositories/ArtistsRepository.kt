package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import android.util.Log
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class ArtistsRepository(private val application: Application) {
    suspend fun refreshData(): List<Artist> {
        // Init cache manager
        val cacheManager = CacheManager.getInstance(application.applicationContext)

        return try {
            // Get info from API
            val artists = NetworkServiceAdapter.getInstance(application).getArtists()
            if (artists.isNotEmpty()) {
                //Update info in cache
                cacheManager.addArtist(artists)
                Log.d("Cache decision", "Data fetched from network and cached")
                artists
            } else {
                // Get artists from cache
                Log.d("Cache decision", "No albums from network, returning cached data")
                cacheManager.getArtists() ?: emptyList()
            }
        } catch (e: Exception) {
            // Get artist from cache
            Log.e("Network error", "Error fetching data from network, returning cached data", e)
            cacheManager.getArtists() ?: emptyList()
        }
    }
}