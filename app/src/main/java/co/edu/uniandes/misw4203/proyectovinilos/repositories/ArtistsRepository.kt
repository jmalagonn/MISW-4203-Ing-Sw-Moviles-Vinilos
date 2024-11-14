package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import android.util.Log
import co.edu.uniandes.misw4203.proyectovinilos.database.dao.ArtistsDao
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter

class ArtistsRepository(private val application: Application, private val artistsDao: ArtistsDao) {

    suspend fun refreshData(): List<Artist> {
        return try {
            // Get artists from API
            val apiData = NetworkServiceAdapter.getInstance(application).getArtists()

            if (apiData.isEmpty()) {
                Log.d("ArtistsRepository", "No data from API, checking cache...")
                // If API fails, get data from cache
                return getCachedArtists()
            } else {
                Log.d("ArtistsRepository", "Data received from API, inserting into database...")
                // Save data in cache room
                artistsDao.insertArtists(apiData)
                Log.d("ArtistsRepository", "Data inserted into database.")
                return apiData
            }
        } catch (e: Exception) {
            Log.e("ArtistsRepository", "Error fetching data from API: ${e.message}")
            // Return cached data in case of an error
            return getCachedArtists()
        }
    }

    // Get cached artists
    private suspend fun getCachedArtists(): List<Artist> {
        val cached = artistsDao.getArtists()
        return if (cached.isEmpty()) {
            Log.d("ArtistsRepository", "No data in cache, returning empty list.")
            emptyList()  // return empty list
        } else {
            Log.d("ArtistsRepository", "Data found in cache, returning.")
            cached  // return cached data
        }
    }
}