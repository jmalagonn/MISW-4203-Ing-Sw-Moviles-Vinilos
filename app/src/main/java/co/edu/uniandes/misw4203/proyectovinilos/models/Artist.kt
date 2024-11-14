package co.edu.uniandes.misw4203.proyectovinilos.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import co.edu.uniandes.misw4203.proyectovinilos.database.dao.Converters
import java.io.Serializable

@Entity(tableName = "artists_table")
@TypeConverters(Converters::class)
data class Artist (
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val albums: List<Album> = emptyList(),
    val birthDate: String,
    val performersPrizes: List<PerformerPrize> = emptyList()
): Serializable