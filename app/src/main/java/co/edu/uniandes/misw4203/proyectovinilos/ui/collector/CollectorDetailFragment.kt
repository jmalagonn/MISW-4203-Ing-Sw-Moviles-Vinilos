package co.edu.uniandes.misw4203.proyectovinilos.ui.collector

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentCollectorDetailBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector
import co.edu.uniandes.misw4203.proyectovinilos.models.Performer
import co.edu.uniandes.misw4203.proyectovinilos.ui.adapters.FavoritePerformerAdapter
import com.bumptech.glide.Glide



class CollectorDetailFragment : Fragment() {

    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!
    private var collectorName: String? = null
    private var collectorTelephone: String? = null
    private var collectorEmail: String? = null
    private var collectorAvatar: String? = null
    private var favoritePerformers: List<Performer>? = null
    private lateinit var favoritePerformersAdapter: FavoritePerformerAdapter
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAdmin = arguments?.getBoolean("isAdmin", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        // Get Attributes from bundle

        val collector = arguments?.getSerializable("collector") as? Collector
        collector?.let {
            collectorName = it.name
            collectorTelephone = it.telephone
            collectorEmail = it.email
            favoritePerformers = it.favoritePerformers

            Log.d("PRUEBA FP", it.favoritePerformers[0].image)

            collectorAvatar = if (!favoritePerformers.isNullOrEmpty()) {
                it.favoritePerformers[0].image
            } else {
                null
            }
        }

        bindAlbumDetails()

        favoritePerformers?.let {
            favoritePerformersAdapter = FavoritePerformerAdapter(it)
            binding.favoritePerformersRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.favoritePerformersRecyclerView.adapter = favoritePerformersAdapter
        }

        // Go back button
        val goBackButton = view.findViewById<Button>(R.id.goBack)
        goBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    private fun bindAlbumDetails() {
        binding.collectorName.text = collectorName
        binding.collectorTel.text = collectorTelephone
        binding.collectorEmail.text = collectorEmail

        // Load Image
        // val coverUrl = collectorAvatar
        Glide.with(this)
            .load(collectorAvatar)
            .placeholder(R.drawable.ic_collector_image)
            .error(R.drawable.ic_collector_image)
            .into(binding.collectorAvatar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}