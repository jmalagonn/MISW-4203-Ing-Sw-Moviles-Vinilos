package co.edu.uniandes.misw4203.proyectovinilos.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentAlbumDetailBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Track
import co.edu.uniandes.misw4203.proyectovinilos.ui.adapters.TrackAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale


class AlbumDetailFragment : Fragment() {
    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private var releaseDate: String? = null
    private var albumTitle: String? = null
    private var albumGenre: String? = null
    private var albumCover: String? = null
    private var albumRecordLabel: String? = null
    private var albumDescription: String? = null
    private var tracks: List<Track>? = null
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
    private lateinit var trackAdapter: TrackAdapter
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAdmin = arguments?.getBoolean("isAdmin", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        // Get Attributes from bundle

        val album = arguments?.getSerializable("album") as? Album
        album?.let {
            albumTitle = it.name
            albumCover = it.cover
            releaseDate = it.releaseDate
            albumGenre = it.genre
            tracks = it.tracks
            albumRecordLabel = it.recordLabel
            albumDescription = it.description
        }

        // Hide option to add button if not admin
        val addAlbumButton = binding.addTrackButton
        if (isAdmin) {
            addAlbumButton.visibility = View.VISIBLE
        } else {
            addAlbumButton.visibility = View.GONE
        }

        bindAlbumDetails()

        // Config Recycler view
        tracks?.let {
            trackAdapter = TrackAdapter(it)
            binding.trackRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.trackRecyclerView.adapter = trackAdapter
        }

        // Go back button
        val goBackButton = view.findViewById<Button>(R.id.goBack)
        goBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    private fun bindAlbumDetails() {
        val date = releaseDate?.let { inputFormat.parse(it) }
        val formattedDate = date?.let { outputFormat.format(it) }
        binding.albumTitle.text = albumTitle
        binding.releaseDate.text = "Lanzamiento $formattedDate"
        binding.genre.text = albumGenre
        binding.recordLabel.text = albumRecordLabel
        binding.description.text = albumDescription

        // Load Image
        val coverUrl = albumCover
        Glide.with(this)
            .load(coverUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .into(binding.albumCover)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
