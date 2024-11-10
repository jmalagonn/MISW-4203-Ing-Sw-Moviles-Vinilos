package co.edu.uniandes.misw4203.proyectovinilos.ui.collector

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentAlbumDetailBinding
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentCollectorDetailBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector
import co.edu.uniandes.misw4203.proyectovinilos.models.CollectorAlbum
import co.edu.uniandes.misw4203.proyectovinilos.models.Comment
import co.edu.uniandes.misw4203.proyectovinilos.models.Performer
import co.edu.uniandes.misw4203.proyectovinilos.ui.adapters.TrackAdapter
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CollectorDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollectorDetailFragment : Fragment() {

    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!
    private var collectorName: String? = null
    private var collectorTelephone: String? = null
    private var collectorEmail: String? = null
    private var collectorAvatar: String? = null
    private var collectorAlbums: List<CollectorAlbum>? = null
    // private lateinit var albumAdapter: CollectorAlbumAdapter
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAdmin = arguments?.getBoolean("isAdmin", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        // Get Attributes from bundle

        val collector = arguments?.getSerializable("collector") as? Collector
        collector?.let {
            collectorName = it.name
            collectorTelephone = it.telephone
            collectorEmail = it.email
            collectorAlbums = it.collectorAlbums
        }


        // Config Recycler view for Collector Albums
        //  collectorAlbums?.let {
        //  albumAdapter = CollectorAlbumAdapter(it)
        //  binding.albumRecyclerView.layoutManager = LinearLayoutManager(context)
        //  binding.albumRecyclerView.adapter = albumAdapter
        // }

        // Go back button
        val goBackButton = view.findViewById<Button>(R.id.goBack)
        goBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}