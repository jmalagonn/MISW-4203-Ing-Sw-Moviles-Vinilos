package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import android.util.Log
import co.edu.uniandes.misw4203.proyectovinilos.database.dao.AlbumsDao
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter

class AlbumsRepository(private val application: Application, private val albumsDao: AlbumsDao) {

    suspend fun refreshData(): List<Album> {
        return try {
            // Get albums from API
            val apiData = NetworkServiceAdapter.getInstance(application).getAlbums()

            if (apiData.isEmpty()) {
                Log.d("AlbumsRepository", "No data from API, checking cache...")
                // If API fails, get data from cache
                return getCachedAlbums()
            } else {
                Log.d("AlbumsRepository", "Data received from API, inserting into database...")
                // Save data in cache room
                albumsDao.insertAlbums(apiData)
                Log.d("AlbumsRepository", "Data inserted into database.")
                return apiData
            }
        } catch (e: Exception) {
            Log.e("AlbumsRepository", "Error fetching data from API: ${e.message}")
            // Return cached data in case of an error
            return getCachedAlbums()
        }
    }

    // Get cached albums
    private suspend fun getCachedAlbums(): List<Album> {
        val cached = albumsDao.getAlbums()
        return if (cached.isEmpty()) {
            Log.d("AlbumsRepository", "No data in cache, returning empty list.")
            emptyList()  // return empty list
        } else {
            Log.d("AlbumsRepository", "Data found in cache, returning.")
            cached  // return cached data
        }
    }
}
