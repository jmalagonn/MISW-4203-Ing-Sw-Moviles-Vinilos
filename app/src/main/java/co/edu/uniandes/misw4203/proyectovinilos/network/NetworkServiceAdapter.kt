package co.edu.uniandes.misw4203.proyectovinilos.network

import android.content.Context
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.models.Comment
import co.edu.uniandes.misw4203.proyectovinilos.models.Performer
import co.edu.uniandes.misw4203.proyectovinilos.models.Track
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://backvynils-q6yc.onrender.com/"
        var instance: NetworkServiceAdapter? = null
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

    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
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
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    fun getArtists(onComplete:(resp:List<Artist>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("musicians",
        { response ->
            val resp = JSONArray(response)
            val list = mutableListOf<Artist>()
            for (i in 0 until resp.length()) {
                val item = resp.getJSONObject(i)

                list.add(i,
                    Artist(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate"),
                        albums = emptyList(),
                        performersPrizes = emptyList(),
                    ))
            }
            onComplete(list)
        },
        {
            onError(it)
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

}