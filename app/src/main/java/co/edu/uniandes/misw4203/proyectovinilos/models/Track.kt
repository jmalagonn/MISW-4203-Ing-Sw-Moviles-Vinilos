package co.edu.uniandes.misw4203.proyectovinilos.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tracks_table")
data class Track(
    @PrimaryKey val trackId:Int,
    val name: String,
    val duration: String
): Serializable