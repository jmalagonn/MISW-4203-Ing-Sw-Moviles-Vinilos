package co.edu.uniandes.misw4203.proyectovinilos.models

import java.io.Serializable

data class Performer(
    val performerId:Int,
    val name:String,
    val image:String,
    val description: String,
    val birthDate:String
): Serializable