package co.edu.uniandes.misw4203.proyectovinilos.ui.album

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
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
    private var album: Album? = null

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
        val cancelButton = binding.cancelTrackButton
        cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        album = arguments?.getSerializable("album") as? Album

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

        trackDurationEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(editable: Editable?) {
            }
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val duration = charSequence.toString()
                if (!isValidDuration(duration)) {
                    trackDurationEditText.error = "Formato inválido (MM:ss)"
                } else {
                    trackDurationEditText.error = null
                }
            }

        })
    }

    private fun addTrackToAlbum(albumId: Int, track: Track) {
        try {
            viewModel.addTrackToAlbum(albumId, track, {
                album?.tracks = album?.tracks?.plus(track) ?: listOf(track)
                Toast.makeText(context, "Track agregado exitosamente", Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putSerializable("album", album)
                    putBoolean("isAdmin",isAdmin)
                }
                findNavController().popBackStack()
                findNavController().navigate(R.id.albumDetailFragment, bundle)

            }, { error ->
                Toast.makeText(context, "Error al agregar el track: $error", Toast.LENGTH_SHORT).show()
            })
        } catch (e: Exception) {
            Toast.makeText(context, "Ocurrió un error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidDuration(duration: String): Boolean {
        // Regex for time validation
        val regex = "^([0-5][0-9]):([0-5][0-9])$".toRegex()
        return duration.matches(regex)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
