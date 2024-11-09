package co.edu.uniandes.misw4203.proyectovinilos.models

data class Artist (
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val albums: List<Album>,
    val birthDate: String,
    val performersPrizes: List<PerformerPrize>
)