package co.edu.uniandes.misw4203.proyectovinilos.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentAddAlbumArtistBinding
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentAddTrackBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.AlbumViewModel
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.ArtistViewModel
import kotlinx.coroutines.launch


class AddAlbumArtistFragment : Fragment() {
    private var isAdmin: Boolean = false
    private val artistViewModel: ArtistViewModel by viewModels()
    private val albumViewModel: AlbumViewModel by viewModels()
    private var artistName: String? = null
    private var artistId: Int? = null
    private var artistBirthDate: String? = null
    private var artistImage: String? = null
    private var artistDescription: String? = null
    private var albums: List<Album>? = null
    private var availableAlbums: List<Album> = emptyList()
    private var _binding: FragmentAddAlbumArtistBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAdmin = arguments?.getBoolean("isAdmin", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAlbumArtistBinding.inflate(inflater, container, false)
        val cancelButton = binding.cancelButton
        cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Obtener el artista de los argumentos
        val artist = arguments?.getSerializable("artist") as? Artist
        artist?.let {
            artistId = it.id
            artistName = it.name
            artistImage = it.image
            artistBirthDate = it.birthDate
            artistDescription = it.description
            albums = it.albums
        }

        // Configurar el Spinner
        val spinner = _binding!!.spinnerAlbums
        val albumList = mutableListOf("Cargando...")
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, albumList)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        spinner.adapter = adapter

        // Observar los datos de álbumes desde el ViewModel
        albumViewModel.albums.observe(viewLifecycleOwner) { albums ->
            albums?.let {
                // Filtrar los álbumes que no están en la lista del artista
                availableAlbums = it.filter { album ->
                    artist?.albums?.none { artistAlbum -> artistAlbum.albumId == album.albumId } == true
                }

                albumList.clear()
                albumList.add("Seleccione un Álbum")
                albumList.addAll(availableAlbums.map { album -> album.name })
                adapter.notifyDataSetChanged()
            }
        }

        loadAlbums()

        // Configurar el botón 'addAlbumButton'
        val addAlbumButton = _binding!!.addAlbumButton
        addAlbumButton.setOnClickListener {
            val selectedAlbum = spinner.selectedItem.toString()
            if (selectedAlbum == "Seleccione un Álbum") {
                Toast.makeText(context, "Por favor, seleccione un álbum", Toast.LENGTH_SHORT).show()
            } else if (artistId != null) {

                val selectedAlbumPosition = spinner.selectedItemPosition
                val album = availableAlbums.getOrNull(selectedAlbumPosition - 1)
                album?.let { selectedAlbum ->
                    val albumId = selectedAlbum.albumId
                    // Llamar al ViewModel para asociar el álbum al artista
                    artistViewModel.associateAlbumToArtist(albumId, artistId!!)
                    Toast.makeText(context, "Álbum agregado correctamente", Toast.LENGTH_SHORT).show()
                    artist?.albums = artist?.albums?.plus(selectedAlbum) ?: listOf(selectedAlbum)
                    val bundle = Bundle().apply {
                        putSerializable("artist", artist)
                        putBoolean("isAdmin", isAdmin)
                    }
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.artistDetailFragment, bundle)
                }
            }
        }

        return binding.root
    }

    private fun loadAlbums() {
        lifecycleScope.launch {
            try {
                albumViewModel.fetchAlbums()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
