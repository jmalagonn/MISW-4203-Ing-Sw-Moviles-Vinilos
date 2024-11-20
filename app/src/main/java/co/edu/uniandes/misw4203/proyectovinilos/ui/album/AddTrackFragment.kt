package co.edu.uniandes.misw4203.proyectovinilos.ui.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentAddTrackBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Track
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.AlbumViewModel

class AddTrackFragment : Fragment() {

    private var albumId: Int? = null
    private lateinit var trackNameEditText: EditText
    private lateinit var trackDurationEditText: EditText
    private lateinit var addTrackButton: Button
    private val viewModel: AlbumViewModel by viewModels()
    private var _binding: FragmentAddTrackBinding? = null
    private val binding get() = _binding!!
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumId = arguments?.getInt("albumId")
        isAdmin = arguments?.getBoolean("isAdmin", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackNameEditText = binding.trackNameEditText
        trackDurationEditText = binding.trackDurationEditText
        addTrackButton = binding.addTrackButton

        addTrackButton.setOnClickListener {
            val trackName = trackNameEditText.text.toString()
            val trackDuration = trackDurationEditText.text.toString()

            if (trackName.isNotEmpty() && trackDuration.isNotEmpty()) {
                val track = Track(name = trackName, duration = trackDuration, trackId = 0)
                addTrackToAlbum(albumId!!, track)
            } else {
                Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addTrackToAlbum(albumId: Int, track: Track) {
        try {
            viewModel.addTrackToAlbum(albumId, track, {
                Toast.makeText(context, "Track agregado exitosamente", Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putBoolean("isAdmin", isAdmin)
                }
                findNavController().navigate(R.id.navigation_album, bundle)

            }, { error ->
                Toast.makeText(context, "Error al agregar el track: $error", Toast.LENGTH_SHORT).show()
            })
        } catch (e: Exception) {
            Toast.makeText(context, "Ocurrió un error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
