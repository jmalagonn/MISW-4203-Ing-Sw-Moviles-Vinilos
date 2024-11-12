package co.edu.uniandes.misw4203.proyectovinilos.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import co.edu.uniandes.misw4203.proyectovinilos.database.dao.Converters
import java.io.Serializable

@Entity(tableName = "collectors_table")
@TypeConverters(Converters::class)
data class Collector(
    @PrimaryKey val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment> = emptyList(),
    val favoritePerformers: List<Performer> = emptyList(),
    val collectorAlbums: List<CollectorAlbum> = emptyList()

): Serializable

@Entity(tableName = "collector_album_table")
data class CollectorAlbum(
    @PrimaryKey val id: Int,
    val price: Double,
    val status: String,
    val album: Album? = null
): Serializable