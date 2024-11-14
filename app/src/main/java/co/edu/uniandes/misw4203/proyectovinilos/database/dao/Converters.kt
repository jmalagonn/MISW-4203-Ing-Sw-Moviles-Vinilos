package co.edu.uniandes.misw4203.proyectovinilos.database.dao
import androidx.room.TypeConverter
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.CollectorAlbum
import co.edu.uniandes.misw4203.proyectovinilos.models.Comment
import co.edu.uniandes.misw4203.proyectovinilos.models.Performer
import co.edu.uniandes.misw4203.proyectovinilos.models.PerformerPrize
import co.edu.uniandes.misw4203.proyectovinilos.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromTrackList(value: List<Track>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTrackList(value: String): List<Track> {
        val gson = Gson()
        val type = object : TypeToken<List<Track>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromPerformerList(value: List<Performer>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPerformerList(value: String): List<Performer> {
        val gson = Gson()
        val type = object : TypeToken<List<Performer>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCommentList(value: List<Comment>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCommentList(value: String): List<Comment> {
        val gson = Gson()
        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromAlbumList(value: List<Album>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAlbumList(value: String): List<Album> {
        val gson = Gson()
        val type = object : TypeToken<List<Album>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromPerformerPrizeList(value: List<PerformerPrize>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPerformerPrizeList(value: String): List<PerformerPrize> {
        val gson = Gson()
        val type = object : TypeToken<List<PerformerPrize>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCollectorAlbumList(value: List<CollectorAlbum>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCollectorAlbumList(value: String): List<CollectorAlbum> {
        val gson = Gson()
        val type = object : TypeToken<List<CollectorAlbum>>() {}.type
        return gson.fromJson(value, type)
    }

}
