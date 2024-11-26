package co.edu.uniandes.misw4203.proyectovinilos.ui.artist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import co.edu.uniandes.misw4203.proyectovinilos.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddAlbumArtistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddAlbumArtistFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_album_artist, container, false)

        val albums = listOf("Seleccione un Álbum","Album 1", "Album 2", "Album 3", "Album 4")

        val spinner: Spinner = view.findViewById(R.id.spinnerAlbums)

        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, albums)

        adapter.setDropDownViewResource(R.layout.spinner_selected_item)

        spinner.adapter = adapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddAlbumArtistFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
