package co.edu.uniandes.misw4203.proyectovinilos.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist

@Dao
interface ArtistsDao {
    @Query("SELECT * FROM artists_table")
    fun getArtists():List<Artist>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artist: Artist)

    @Query("DELETE FROM artists_table")
    suspend fun deleteAll():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists : List<Artist>)
}