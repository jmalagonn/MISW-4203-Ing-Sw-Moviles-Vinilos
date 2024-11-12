package co.edu.uniandes.misw4203.proyectovinilos.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.io.Serializable

@Entity(tableName = "comments_table")
data class Comment(
    @PrimaryKey val commentId:Int,
    val description:String,
    val rating:String
): Serializable