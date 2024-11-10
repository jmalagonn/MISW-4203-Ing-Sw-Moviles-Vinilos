package co.edu.uniandes.misw4203.proyectovinilos.models

import java.io.Serializable

data class Comment(
    val commentId:Int,
    val description:String,
    val rating:String
): Serializable