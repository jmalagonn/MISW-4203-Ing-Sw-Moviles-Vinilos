package co.edu.uniandes.misw4203.proyectovinilos.models

import java.io.Serializable
import java.util.Date

data class Musician(
    val musicianId:Int,
    val name:String,
    val image:String,
    val description: String,
    val birthDate:Date,
    val albums: List<Album> = emptyList()
): Serializable