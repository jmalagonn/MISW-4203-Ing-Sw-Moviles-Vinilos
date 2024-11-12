package co.edu.uniandes.misw4203.proyectovinilos.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table")
    fun getCollectors():List<Collector>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collectors: Collector)

    @Query("DELETE FROM collectors_table")
    suspend fun deleteAll():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectors(collectors : List<Collector>)
}