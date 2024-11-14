package co.edu.uniandes.misw4203.proyectovinilos.repositories

import android.app.Application
import android.util.Log
import co.edu.uniandes.misw4203.proyectovinilos.database.dao.CollectorsDao
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector
import co.edu.uniandes.misw4203.proyectovinilos.network.NetworkServiceAdapter
import com.android.volley.VolleyError

class CollectorsRepository (private val application: Application, private val collectorDao: CollectorsDao){
    suspend fun refreshData(): List<Collector> {
        return try {
            // Get albums from API
            val apiData = NetworkServiceAdapter.getInstance(application).getCollectors()

            if (apiData.isEmpty()) {
                Log.d("CollectorsRepository", "No data from API, checking cache...")
                // If API fails, get data from cache
                return getCachedCollectors()
            } else {
                Log.d("CollectorsRepository", "Data received from API, inserting into database...")
                // Save data in cache room
                collectorDao.insertCollectors(apiData)
                Log.d("CollectorsRepository", "Data inserted into database.")
                return apiData
            }
        } catch (e: Exception) {
            Log.e("CollectorsRepository", "Error fetching data from API: ${e.message}")
            // Return cached data in case of an error
            return getCachedCollectors()
        }
    }

    // Get cached Collectors
    private suspend fun getCachedCollectors(): List<Collector> {
        val cached = collectorDao.getCollectors()
        return if (cached.isEmpty()) {
            Log.d("CollectorsRepository", "No data in cache, returning empty list.")
            emptyList()  // return empty list
        } else {
            Log.d("CollectorsRepository", "Data found in cache, returning.")
            cached  // return cached data
        }
    }
}
