package ma.n1akai.edusync.ui.home.mark

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.databinding.FragmentHomeworkBinding
import ma.n1akai.edusync.databinding.FragmentMarkBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.ui.home.dashboard.DashboardAdapter
import ma.n1akai.edusync.ui.home.dashboard.DashboardViewModel
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class MarkFragment : BaseFragment<FragmentMarkBinding>() {

    private val viewModel: MarkViewModel by viewModels()
    private val markAdapter = MarkAdapter()

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMarkBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testsObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.markRcMarks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = markAdapter
        }
    }


    private fun testsObserver() {
        viewModel.tests.observe(this) {
            when (it) {
                is UiState.Loading -> binding.markProgress.show()

                is UiState.Success -> {
                    binding.markProgress.hide()
                    markAdapter.items = it.data
                }

                is UiState.Failure -> {
                    binding.markProgress.hide()
                    binding.root.snackbar(it.error!!)
                }
            }
        }
    }

}