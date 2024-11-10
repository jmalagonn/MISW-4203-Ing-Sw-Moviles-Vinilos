package co.edu.uniandes.misw4203.proyectovinilos.ui.artist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentArtistDetailBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.ui.adapters.ArtistAlbumAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale


class ArtistDetailFragment : Fragment() {
    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get() = _binding!!
    private var artistName: String? = null
    private var artistBirthDate: String? = null
    private var artistImage: String? = null
    private var artistDescription: String? = null
    private var albums: List<Album>? = null
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
    private lateinit var artistAlbumAdapter: ArtistAlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        // Get Attributes from bundle

        val artist = arguments?.getSerializable("artist") as? Artist
        artist?.let {
            artistName = it.name
            artistImage = it.image
            artistBirthDate = it.birthDate
            artistDescription = it.description
            albums = it.albums
        }

        bindArtistDetails()

        albums?.let {
            artistAlbumAdapter = ArtistAlbumAdapter(it)
            binding.albumRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.albumRecyclerView.adapter = artistAlbumAdapter
        }

        // Go back button
        val goBackButton = view.findViewById<Button>(R.id.goBack)
        goBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return view
    }

    private fun bindArtistDetails(){
        val date = artistBirthDate?.let { inputFormat.parse(it) }
        val formattedDate = date?.let { outputFormat.format(it) }
        binding.artistName.text = artistName
        binding.birthDate.text = "Fecha de nacimiento $formattedDate"
        binding.description.text = artistDescription

        // Load Image
        val coverUrl = artistImage
        Glide.with(this)
            .load(coverUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .into(binding.artistImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}