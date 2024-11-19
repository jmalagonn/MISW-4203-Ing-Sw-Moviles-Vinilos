package co.edu.uniandes.misw4203.proyectovinilos.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentCreateAlbumBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.AlbumViewModel

class CreateAlbumFragment : Fragment() {

    private var _binding: FragmentCreateAlbumBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(requireActivity().application))
            .get(AlbumViewModel::class.java)

        binding.saveButton.setOnClickListener {
            val name = binding.albumNameInput.text.toString()
            val cover = binding.albumCoverInput.text.toString()
            val releaseDate = binding.albumReleaseDateInput.text.toString()
            val description = binding.albumDescriptionInput.text.toString()
            val genre = binding.albumGenreInput.text.toString()
            val recordLabel = binding.albumRecordLabelInput.text.toString()

            if (name.isNotEmpty() && cover.isNotEmpty() && releaseDate.isNotEmpty() &&
                description.isNotEmpty() && genre.isNotEmpty() && recordLabel.isNotEmpty()
            ) {
                val album = Album(
                    albumId = 0,
                    name = name,
                    cover = cover,
                    releaseDate = releaseDate,
                    description = description,
                    genre = genre,
                    recordLabel = recordLabel
                )

                createAlbum(album)
            } else {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createAlbum(album: Album) {
        viewModel.createAlbum(
            album,
            onSuccess = {
                Toast.makeText(requireContext(), "Álbum creado con éxito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.navigation_album)
            },
            onError = { errorMessage ->
                Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
