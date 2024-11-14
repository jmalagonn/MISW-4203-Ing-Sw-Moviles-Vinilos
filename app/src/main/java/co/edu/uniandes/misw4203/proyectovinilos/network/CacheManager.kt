import android.content.Context
import android.util.LruCache
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist


class CacheManager private constructor(context: Context) {

    private val albumCache: LruCache<String, List<Album>>
    private val artistCache: LruCache<String, List<Artist>>

    init {
        val cacheSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
        albumCache = LruCache(cacheSize)
        artistCache = LruCache(cacheSize)
    }

    companion object {
        private var instance: CacheManager? = null

        fun getInstance(context: Context): CacheManager {
            return instance ?: synchronized(this) {
                instance ?: CacheManager(context).also { instance = it }
            }
        }
    }

    fun addAlbums(albums: List<Album>) {
        albumCache.put("albums", albums)
    }

    fun getAlbums(): List<Album>? {
        return albumCache.get("albums")
    }

    fun addArtist(artists: List<Artist>){
        artistCache.put("artists", artists)
    }

    fun getArtists(): List<Artist>{
        return artistCache.get("artists")
    }
}