package ma.n1akai.edusync.ui.home.feedetails

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentFeeDetailsBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class FeeDetailsFragment : BaseFragment<FragmentFeeDetailsBinding>(), SwipeRefreshLayout.OnRefreshListener {


    private val viewModel: FeeDetailsViewModel by viewModels()
    private val feeAdapter = FeeAdapter()


     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         binding.feeSwipeRefresh.setOnRefreshListener(this)
         binding.feeRcFees.apply {
             adapter = feeAdapter
             setHasFixedSize(true)
             layoutManager = LinearLayoutManager(requireActivity())
         }
         viewModel.fees.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Failure -> {
                    binding.feeSwipeRefresh.isRefreshing = false
                    binding.feeProgress.hide()
                    binding.root.snackbar(it.error!!)
                }
                UiState.Loading -> {
                    if (!binding.feeSwipeRefresh.isRefreshing) {
                        binding.feeProgress.show()
                    }
                }
                is UiState.Success -> {
                    binding.feeSwipeRefresh.isRefreshing = false
                    binding.feeProgress.hide()
                    feeAdapter.items = it.data
                }
            }
         }
     }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFeeDetailsBinding.inflate(inflater, container, false)

    override fun onRefresh() {
        viewModel.getFees()
    }
}