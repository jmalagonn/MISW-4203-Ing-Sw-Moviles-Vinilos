package co.edu.uniandes.misw4203.proyectovinilos.ui.collector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import co.edu.uniandes.misw4203.proyectovinilos.databinding.FragmentCollectorBinding
import co.edu.uniandes.misw4203.proyectovinilos.viewmodels.CollectorViewModel

class CollectorFragment : Fragment() {

    private var _binding: FragmentCollectorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val collectorViewModel =
            ViewModelProvider(this).get(CollectorViewModel::class.java)

        _binding = FragmentCollectorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCollector
        collectorViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}