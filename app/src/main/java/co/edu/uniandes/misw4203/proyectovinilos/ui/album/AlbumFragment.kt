package co.edu.uniandes.misw4203.proyectovinilos.ui.album

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import co.edu.uniandes.misw4203.proyectovinilos.MainActivity
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentAlbumBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Album
import co.edu.uniandes.misw4203.proyectovinilos.ui.adapters.AlbumsAdapter
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null
    private var albumList: List<Album> = emptyList()
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAdmin = arguments?.getBoolean("isAdmin", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val addAlbumButton: Button = binding.addAlbumButton
        addAlbumButton.setOnClickListener {
            val createAlbumFragment = CreateAlbumFragment()

            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val bundle = Bundle().apply {
                putBoolean("isAdmin",isAdmin)
            }
            findNavController().navigate(R.id.createAlbumFragment,bundle)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide option to add button if not admin
        val addAlbumButton = binding.addAlbumButton
        if (isAdmin) {
            addAlbumButton.visibility = View.VISIBLE
        } else {
            addAlbumButton.visibility = View.GONE
        }

        binding.progressBar.visibility = View.VISIBLE
        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.albumsRv.layoutManager = gridLayoutManager

        viewModelAdapter = AlbumsAdapter { album -> showAlbumDetail(album) }
        binding.albumsRv.adapter = viewModelAdapter

        // Search field
        binding.searchAlbumEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterAlbums(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToLogin()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application))[AlbumViewModel::class.java]
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            Log.d("AlbumFragment", "Received albums: ${albums.size}")
            albumList = albums
            viewModelAdapter?.albums = albums
            binding.progressBar.visibility = View.GONE

            // Show Counter albums
            binding.totalAlbumsTextView.text =
                getString(R.string.total_albums_counter, albums.size.toString())
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun filterAlbums(query: String) {
        Log.d("AlbumFragment", "Filtering albums with query: $query, total albums: ${albumList.size}")

        val filteredList = albumList.filter { album ->
            album.name.contains(query, ignoreCase = true)
        }

        viewModelAdapter?.albums = filteredList
        binding.totalAlbumsTextView.text = getString(R.string.total_albums_counter, filteredList.size.toString())
    }

    private fun showAlbumDetail(album: Album) {
        val bundle = Bundle().apply {
            putSerializable("album", album)
            putBoolean("isAdmin",isAdmin)
        }
        findNavController().navigate(R.id.albumDetailFragment, bundle)
    }

    private fun navigateToLogin() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
