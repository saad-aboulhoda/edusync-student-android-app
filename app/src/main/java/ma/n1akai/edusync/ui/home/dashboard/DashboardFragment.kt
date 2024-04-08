package ma.n1akai.edusync.ui.home.dashboard

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentDashboardBinding
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val dashboardAdapter = DashboardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashboardRc.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (dashboardAdapter.getItemViewType(position)) {
                            R.layout.notes_list_item -> 1
                            else -> 2
                        }
                    }

                }
            }
            adapter = dashboardAdapter
        }

        viewModel.dashboardListItemsData.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> binding.dashboardProgress.show()

                is UiState.Success -> {
                    binding.dashboardProgress.hide()
                    dashboardAdapter.items = it.data
                }

                is UiState.Failure -> {
                    binding.dashboardProgress.hide()
                    binding.root.snackbar(it.error!!)
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}