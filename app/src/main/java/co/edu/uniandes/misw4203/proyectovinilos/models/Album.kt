package co.edu.uniandes.misw4203.proyectovinilos.models

import java.io.Serializable

data class Track(
    val trackId:Int,
    val name: String,
    val duration: String
): Serializable

data class Performer(
    val performerId:Int,
    val name:String,
    val image:String,
    val description: String,
    val birthDate:String
): Serializable

data class Comment(
    val commentId:Int,
    val description:String,
    val rating:String
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