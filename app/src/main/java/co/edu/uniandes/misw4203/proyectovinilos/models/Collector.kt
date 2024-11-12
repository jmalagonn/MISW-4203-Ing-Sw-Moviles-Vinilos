package co.edu.uniandes.misw4203.proyectovinilos.models

import java.io.Serializable

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment> = emptyList(),
    val favoritePerformers: List<Performer> = emptyList(),
    val collectorAlbums: List<CollectorAlbum> = emptyList()

): Serializable

data class CollectorAlbum(
    val id: Int,
    val price: Double,
    val status: String,
    val album: Album? = null
): Serializable