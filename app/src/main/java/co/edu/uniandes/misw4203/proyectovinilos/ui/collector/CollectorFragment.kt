package co.edu.uniandes.misw4203.proyectovinilos.ui.collector

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import co.edu.uniandes.misw4203.proyectovinilos.R
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentCollectorBinding
import co.edu.uniandes.misw4203.proyectovinilos.models.Collector
import co.edu.uniandes.misw4203.proyectovinilos.ui.adapters.CollectorsAdapter
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.CollectorViewModel


class CollectorFragment : Fragment() {
    private var _binding: FragmentCollectorBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorsAdapter? = null
    private var collectorList: List<Collector> = emptyList()
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAdmin = arguments?.getBoolean("isAdmin", false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide option to add button if not admin
        val addCollectorButton = binding.addCollectorButton
        if (isAdmin) {
            addCollectorButton.visibility = View.VISIBLE
        } else {
            addCollectorButton.visibility = View.GONE
        }

        binding.progressBar.visibility = View.VISIBLE
        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.collectorsRv.layoutManager = gridLayoutManager

        viewModelAdapter = CollectorsAdapter { collector -> showCollectorDetail(collector) }
        binding.collectorsRv.adapter = viewModelAdapter

        // Search field
        binding.searchCollectorEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCollectors(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application))[CollectorViewModel::class.java]

        viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            Log.d("CollectorsFragment", "Received collectors: ${collectors.size}")
            collectorList = collectors
            viewModelAdapter?.collectors = collectors
            binding.progressBar.visibility = View.GONE

            // Show Counter collectors
            binding.totalCollectorsTextView.text =
                getString(R.string.total_collectors_counter, collectors.size.toString())
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

    private fun filterCollectors(query: String) {
        Log.d("CollectorsFragment", "Filtering collectors with query: $query, total collectors: ${collectorList.size}")

        val filteredList = collectorList.filter { collector ->
            collector.name.contains(query, ignoreCase = true)
        }

        viewModelAdapter?.collectors = filteredList
        binding.totalCollectorsTextView.text = getString(R.string.total_collectors_counter,filteredList.size.toString())

    }

    private fun showCollectorDetail(collector: Collector) {
        val bundle = Bundle().apply {
            putSerializable("collector", collector)
            putBoolean("isAdmin", isAdmin)
        }
        findNavController().navigate(R.id.collectorDetailFragment, bundle)
    }
}