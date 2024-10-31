package co.edu.uniandes.misw4203.proyectovinilos.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentAlbumDetailBinding
import com.bumptech.glide.Glide

class AlbumDetailFragment : Fragment() {
    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private var albumId: String? = null
    private var albumTitle: String? = null
    private var albumArtist: String? = null
    private var albumCover: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        // Get Attributes from bundle
        arguments?.let {
            albumId = it.getString("album_id")
            albumTitle = it.getString("album_title")
            albumArtist = it.getString("album_artist")
            albumCover = it.getString("album_cover")
        }

        bindAlbumDetails()

        return view
    }

    private fun bindAlbumDetails() {
        binding.albumTitleTextView.text = albumTitle
        binding.albumArtistTextView.text = albumArtist

        // Load Image
        val coverUrl = albumCover
        Glide.with(this)
            .load(coverUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .into(binding.albumCoverImageView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
