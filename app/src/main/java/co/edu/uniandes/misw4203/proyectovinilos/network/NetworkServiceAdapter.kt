package co.edu.uniandes.misw4203.proyectovinilos.network

import android.content.Context
import android.util.Log
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector
import co.edu.uniandes.misw4203.proyectovinilos.models.CollectorAlbum
import co.edu.uniandes.misw4203.proyectovinilos.models.Comment
import co.edu.uniandes.misw4203.proyectovinilos.models.Performer
import co.edu.uniandes.misw4203.proyectovinilos.models.Track
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter(context: Context) {
    companion object{
        const val BASE_URL= "https://backvynils-q6yc.onrender.com/"
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun associateAlbumToArtist(
        albumId: Int,
        artistId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val jsonBody = JSONObject().apply {
            put("id", artistId)
        }

        Log.d("NetworkRequest", "Making POST request to associate album $albumId with artist $artistId")

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            BASE_URL+"albums/$albumId/musicians/$artistId/",
            null,
            { response ->
                Log.d("NetworkRequest", "Album associated successfully with artist: $response")
                onSuccess()
            },
            { error ->
                val errorMessage: String = when {
                    error.networkResponse != null -> {
                        val statusCode = error.networkResponse.statusCode
                        val responseBody = String(error.networkResponse.data)
                        Log.e("NetworkRequest", "Error $statusCode: $responseBody")
                        "Error $statusCode: $responseBody"
                    }
                    else -> {
                        val unknownError = "Error desconocido al asociar álbum con artista"
                        Log.e("NetworkRequest", unknownError)
                        unknownError
                    }
                }
                onError(errorMessage)
            }
        )

        requestQueue.add(jsonRequest)
    }

    //Setting coroutine
    //Optimization
    suspend fun getAlbums()= suspendCoroutine<List<Album>>{ cont->
        val list = mutableListOf<Album>()
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                var item: JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    val tracks = if (item.has("tracks")) item.getJSONArray("tracks").toTrackList() else emptyList()
                    val performers = if (item.has("performers")) item.getJSONArray("performers").toPerformerList() else emptyList()
                    val comments = if (item.has("comments")) item.getJSONArray("comments").toCommentList() else emptyList()

                    list.add(i,
                        Album(albumId = item.getInt("id"),
                            name = item.getString("name"),
                            cover = item.getString("cover"),
                            recordLabel = item.getString("recordLabel"),
                            releaseDate = item.getString("releaseDate"),
                            genre = item.getString("genre"),
                            description = item.getString("description"),
                            tracks = tracks,
                            comments = comments,
                            performers = performers
                        ))
                }
                cont.resume(list)

            }, {
                cont.resumeWithException(it)
            }))
    }

    //Optimization
    suspend fun getArtists() = suspendCoroutine<List<Artist>>{ cont->
        val list = mutableListOf<Artist>()
        requestQueue.add(getRequest("musicians",
        { response ->
            val resp = JSONArray(response)
            var item: JSONObject? = null
            for (i in 0 until resp.length()) {
                item = resp.getJSONObject(i)
                val albums = if (item.has("albums")) item.getJSONArray("albums").toAlbumList() else emptyList()

                list.add(i,
                    Artist(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate"),
                        albums = albums,
                        performersPrizes = emptyList(),
                    ))
            }
            cont.resume(list)

        }, {
                cont.resumeWithException(it)
            }))
    }

    //Optimization
    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
        val list = mutableListOf<Collector>()
        requestQueue.add(getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                var item: JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    val collectorAlbums = if (item.has("collectorAlbums")) item.getJSONArray("collectorAlbums").toCollectorAlbumsList() else emptyList()
                    val favoritePerformers = if (item.has("favoritePerformers")) item.getJSONArray("favoritePerformers").toPerformerList() else emptyList()
                    val comments = if (item.has("comments")) item.getJSONArray("comments").toCommentList() else emptyList()

                    list.add(i,
                        Collector(id = item.getInt("id"),
                            name = item.getString("name"),
                            telephone = item.getString("telephone"),
                            email = item.getString("email"),
                            comments = comments,
                            favoritePerformers = favoritePerformers,
                            collectorAlbums = collectorAlbums
                        ))
                }
                cont.resume(list)

            }, {
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    private fun JSONArray.toTrackList(): List<Track> {
        val list = mutableListOf<Track>()
        for (i in 0 until this.length()) {
            val track = this.getJSONObject(i)
            list.add(
                Track(
                    trackId = track.getInt("id"),
                    name = track.getString("name"),
                    duration = track.getString("duration")
                )
            )
        }
        return list
    }

    private fun JSONArray.toAlbumList(): List<Album> {
        val list = mutableListOf<Album>()
        for (i in 0 until this.length()) {
            val album = this.getJSONObject(i)
            list.add(
                Album(
                    albumId = album.getInt("id"),
                    name = album.getString("name"),
                    cover = album.getString("cover"),
                    recordLabel = album.getString("recordLabel"),
                    releaseDate = album.getString("releaseDate"),
                    genre = album.getString("genre"),
                    description = album.getString("description"),
                )
            )
        }
        return list
    }

    private fun JSONArray.toPerformerList(): List<Performer> {
        val list = mutableListOf<Performer>()
        for (i in 0 until this.length()) {
            val performer = this.getJSONObject(i)
            list.add(
                Performer(
                    performerId = performer.optInt("id", -1),
                    name = performer.optString("name", ""),
                    image = performer.optString("image", ""),
                    description = performer.optString("description", ""),
                    birthDate = performer.optString("birthDate", "")
                )
            )
        }
        return list
    }

    private fun JSONArray.toCommentList(): List<Comment> {
        val list = mutableListOf<Comment>()
        for (i in 0 until this.length()) {
            val comment = this.getJSONObject(i)
            list.add(
                Comment(
                    commentId = comment.getInt("id"),
                    description = comment.getString("description"),
                    rating = comment.getString("rating")
                )
            )
        }
        return list
    }

    private fun JSONArray.toCollectorAlbumsList(): List<CollectorAlbum> {
        val list = mutableListOf<CollectorAlbum>()
        for (i in 0 until this.length()) {
            val collectorAlbum = this.getJSONObject(i)
            list.add(
                CollectorAlbum(
                    id = collectorAlbum.getInt("id"),
                    price = collectorAlbum.getDouble("price"),
                    status = collectorAlbum.getString("status")
                )
            )
        }
        return list
    }

    suspend fun createAlbum(album: Album): Result<Unit> {
        return suspendCoroutine { continuation ->
            val jsonBody = JSONObject().apply {
                put("name", album.name)
                put("cover", album.cover)
                put("releaseDate", album.releaseDate)
                put("description", album.description)
                put("genre", album.genre)
                put("recordLabel", album.recordLabel)
            }

            Log.d("NetworkRequest", "Making POST request with body: $jsonBody")

            val jsonRequest = JsonObjectRequest(
                Request.Method.POST, BASE_URL + "albums", jsonBody,
                { response ->
                    continuation.resume(Result.success(Unit)) // En caso de éxito
                },
                { error ->
                    val errorMessage: String = when {
                        error.networkResponse != null -> {
                            val statusCode = error.networkResponse.statusCode
                            val responseBody = String(error.networkResponse.data)

                            Log.e("NetworkRequest", "Error $statusCode: $responseBody")
                            "Error $statusCode: $responseBody"
                        }
                        else -> {
                            val unknownError = "Error desconocido"
                            Log.e("NetworkRequest", unknownError)
                            unknownError
                        }
                    }
                    continuation.resume(Result.failure(Exception(errorMessage)))
                }
            )

            requestQueue.add(jsonRequest)
        }
    }

    suspend fun addTrackToAlbum(albumId: Int, track: Track): Result<Unit> {
        return suspendCoroutine { continuation ->
            val jsonBody = JSONObject().apply {
                put("name", track.name)
                put("duration", track.duration)
            }

            Log.d("NetworkRequest", "Making POST request with body: $jsonBody")

            val jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "albums/$albumId/tracks",
                jsonBody,
                { response ->
                    Log.d("NetworkRequest", "Track added successfully: $response")
                    continuation.resume(Result.success(Unit))
                },
                { error ->
                    val errorMessage = when {
                        error.networkResponse != null -> {
                            val statusCode = error.networkResponse.statusCode
                            val responseBody = String(error.networkResponse.data)

                            Log.e("NetworkRequest", "Error $statusCode: $responseBody")
                            "Error $statusCode: $responseBody"
                        }
                        else -> {
                            val unknownError = "Error desconocido"
                            Log.e("NetworkRequest", unknownError)
                            unknownError
                        }
                    }
                    continuation.resume(Result.failure(Exception(errorMessage)))
                }
            )

            requestQueue.add(jsonRequest)
        }
    }


}