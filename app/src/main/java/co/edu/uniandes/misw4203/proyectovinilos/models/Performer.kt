package co.edu.uniandes.misw4203.proyectovinilos.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "performers_table")
data class Performer(
    @PrimaryKey val performerId:Int,
    val name:String,
    val image:String,
    val description: String,
    val birthDate:String
): Serializable