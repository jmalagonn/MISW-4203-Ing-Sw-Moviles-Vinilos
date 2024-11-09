package co.edu.uniandes.misw4203.proyectovinilos.ui.artist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentArtistBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Artist
import co.edu.uniandes.misw4203.proyectovinilos.ui.adapters.AlbumsAdapter
import co.edu.uniandes.misw4203.proyectovinilos.ui.adapters.ArtistsAdapter
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.ArtistViewModel

class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!
    private lateinit var _viewModel: ArtistViewModel
    private var _viewModelAdapter: ArtistsAdapter? = null
    private var _artistsList: List<Artist> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.artistRv.layoutManager = gridLayoutManager

        _viewModelAdapter = ArtistsAdapter()
        binding.artistRv.adapter = _viewModelAdapter

        // Search field
        binding.searchArtistEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterArtists(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        _viewModel = ViewModelProvider(this).get(
            ArtistViewModel::class.java)

        _viewModel.artists.observe(viewLifecycleOwner, Observer<List<Artist>> { artists ->
            Log.d("ArtistFragment", "Received albums: ${artists.size}")
            _artistsList = artists
            _viewModelAdapter?.artists = artists
            binding.progressBar.visibility = View.GONE

            // Show Counter albums
            binding.totalArtistsTextView.text = "Total de artistas: ${artists.size}"
        })

        _viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!_viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            _viewModel.onNetworkErrorShown()
        }
    }

    private fun filterArtists(query: String) {
        Log.d("ArtistFragment", "Filtering artists with query: $query, total artists: ${_artistsList.size}")

        val filteredList = _artistsList.filter { album ->
            album.name.contains(query, ignoreCase = true)
        }

        _viewModelAdapter?.artists = filteredList
        binding.totalArtistsTextView.text = "Total de artistas: ${filteredList.size}"
    }
}