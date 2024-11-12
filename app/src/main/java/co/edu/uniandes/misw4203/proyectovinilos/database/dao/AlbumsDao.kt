package co.edu.uniandes.misw4203.proyectovinilos.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.uniandes.misw4203.proyectovinilos.models.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table")
    fun getAlbums():List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)
}