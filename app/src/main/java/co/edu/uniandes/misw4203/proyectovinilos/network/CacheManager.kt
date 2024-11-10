import android.content.Context
import co.edu.uniandes.misw4203.proyectovinilos.models.Album


class CacheManager private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: CacheManager? = null

        fun getInstance(context: Context): CacheManager =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    // Save albums using ID
    private var albums: List<Album>? = null

    // Save albums in cache
    fun addAlbums(albumList: List<Album>) {
        albums = albumList
    }

    // Get albums from cache
    fun getAlbums(): List<Album> {
        return albums ?: emptyList()
    }

}
