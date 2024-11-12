package co.edu.uniandes.misw4203.proyectovinilos.models

import java.io.Serializable
import androidx.room.*
import co.edu.uniandes.misw4203.proyectovinilos.database.dao.Converters

@Entity(tableName = "albums_table")
@TypeConverters(Converters::class)
data class Album(
    @PrimaryKey val albumId: Int,
    val name: String,
    val duration: String
): Serializable

data class Album (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val tracks:List<Track> = emptyList(),
    val performers:List<Performer> = emptyList(),
    val comments:List<Comment> = emptyList()
): Serializable