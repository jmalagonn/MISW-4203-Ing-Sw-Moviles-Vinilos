package co.edu.uniandes.misw4203.proyectovinilos.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentArtistBinding
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.ArtistViewModel

class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val artistViewModel =
            ViewModelProvider(this).get(ArtistViewModel::class.java)

        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textArtist
        artistViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}