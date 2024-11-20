package co.edu.uniandes.misw4203.proyectovinilos.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
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
    private var albumId: Int? = null
    private var albumTitle: String? = null
    private var albumGenre: String? = null
    private var albumCover: String? = null
    private var albumRecordLabel: String? = null
    private var albumDescription: String? = null
    private var tracks: MutableList<Track> = mutableListOf()
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

        val album = arguments?.getSerializable("album") as? Album
        album?.let {
            albumId = it.albumId
            albumTitle = it.name
            albumCover = it.cover
            releaseDate = it.releaseDate
            albumGenre = it.genre
            tracks.addAll(it.tracks)
            albumRecordLabel = it.recordLabel
            albumDescription = it.description
        }

        val addAlbumButton = binding.addTrackButton
        if (isAdmin) {
            addAlbumButton.visibility = View.VISIBLE
        } else {
            addAlbumButton.visibility = View.GONE
        }

        bindAlbumDetails()

        trackAdapter = TrackAdapter(tracks)
        binding.trackRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.trackRecyclerView.adapter = trackAdapter

        val goBackButton = view.findViewById<Button>(R.id.goBack)
        goBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val addTrackButton: Button = binding.addTrackButton
        addTrackButton.setOnClickListener {
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val bundle = Bundle().apply {
                putBoolean("isAdmin", isAdmin)
                putInt("albumId", albumId!!)
            }
            findNavController().navigate(R.id.addTrackFragment, bundle)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    private fun bindAlbumDetails() {
        val date = releaseDate?.let { inputFormat.parse(it) }
        val formattedDate = date?.let { outputFormat.format(it) }
        binding.albumTitle.text = albumTitle
        binding.releaseDate.text = getString(R.string.release_date, formattedDate)
        binding.genre.text = albumGenre
        binding.recordLabel.text = albumRecordLabel
        binding.description.text = albumDescription


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
