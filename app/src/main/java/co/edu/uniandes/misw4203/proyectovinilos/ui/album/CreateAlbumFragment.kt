package co.edu.uniandes.misw4203.proyectovinilos.ui.album

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentCreateAlbumBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.AlbumViewModel
import java.text.SimpleDateFormat
import java.util.*

class CreateAlbumFragment : Fragment() {

    private var _binding: FragmentCreateAlbumBinding? = null
    private val binding get() = _binding!!
    private var isAdmin: Boolean = false

    private lateinit var viewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAdmin = arguments?.getBoolean("isAdmin", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAlbumBinding.inflate(inflater, container, false)

        // Go back button
        val cancelButton = binding.root.findViewById<Button>(R.id.cancel_album_button)
        cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(requireActivity().application))
            .get(AlbumViewModel::class.java)

        // Validación de formato de fecha para el campo albumReleaseDateInput
        binding.albumReleaseDateInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && !isValidDateFormat(s.toString())) {
                    binding.albumReleaseDateInput.error = "Ingresa una fecha en formato yyyy-MM-dd."
                } else {
                    binding.albumReleaseDateInput.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.saveButton.setOnClickListener {
            val name = binding.albumNameInput.text.toString()
            val cover = binding.albumCoverInput.text.toString()
            val releaseDate = binding.albumReleaseDateInput.text.toString()
            val description = binding.albumDescriptionInput.text.toString()
            val genre = binding.albumGenreInput.text.toString()
            val recordLabel = binding.albumRecordLabelInput.text.toString()

            if (name.isNotEmpty() && cover.isNotEmpty() && releaseDate.isNotEmpty() &&
                description.isNotEmpty() && genre.isNotEmpty() && recordLabel.isNotEmpty() &&
                isValidDateFormat(releaseDate) && isValidImageUrl(cover)
            ) {

                val formattedReleaseDate = convertDateToApiFormat(releaseDate)

                val album = Album(
                    albumId = 0,
                    name = name,
                    cover = cover,
                    releaseDate = formattedReleaseDate,
                    description = description,
                    genre = genre,
                    recordLabel = recordLabel
                )

                createAlbum(album)
            } else {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createAlbum(album: Album) {
        viewModel.createAlbum(
            album,
            onSuccess = {
                Toast.makeText(requireContext(), "Álbum creado con éxito", Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putBoolean("isAdmin",isAdmin)
                }
                findNavController().navigate(R.id.navigation_album, bundle)
            },
            onError = { errorMessage ->
                Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun isValidDateFormat(date: String): Boolean {
        val regex = """\d{4}-\d{2}-\d{2}""".toRegex()
        return regex.matches(date)
    }


    private fun isValidImageUrl(url: String): Boolean {
        val imageExtensions = listOf("jpg", "jpeg", "png", "gif", "bmp", "webp")
        return Patterns.WEB_URL.matcher(url).matches() && imageExtensions.any { url.endsWith(it, true) }
    }


    private fun convertDateToApiFormat(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate: Date = inputFormat.parse(date) ?: return ""

        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("GMT-5")

        val formattedDate = outputFormat.format(parsedDate)

        return "$formattedDate-05:00"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
