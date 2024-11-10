package co.edu.uniandes.misw4203.proyectovinilos.network

import android.content.Context
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector
import co.edu.uniandes.misw4203.proyectovinilos.models.CollectorAlbum
import co.edu.uniandes.misw4203.proyectovinilos.models.Comment
import co.edu.uniandes.misw4203.proyectovinilos.models.Performer
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class CollectorNetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://backvynils-q6yc.onrender.com/"
        var instance: CollectorNetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CollectorNetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
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
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
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