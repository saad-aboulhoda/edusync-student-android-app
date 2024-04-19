package ma.n1akai.edusync.ui.home.examination

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.TestOnline
import ma.n1akai.edusync.databinding.FragmentExaminationBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.ui.home.feedetails.FeeAdapter
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class ExaminationFragment : BaseFragment<FragmentExaminationBinding>(), OnRefreshListener {


    private val viewModel: ExaminationViewModel by viewModels()
    private val examinationAdapter = ExaminationAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener(this)
        viewModel.getTestsOnline()
        examinationAdapter.listener = object : ExaminationAdapter.OnStartTestClickListener {
            override fun onStartTestClick(testOnline: TestOnline, view: View) {
                findNavController()
                    .navigate(
                        ExaminationFragmentDirections.actionExaminationFragmentToExamFragment(
                            testOnline, testOnline.test_online_name
                        )
                    )
            }

        }

        binding.examinationRcExams.apply {
            adapter = examinationAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }
        viewModel.tests.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.examProgress.hide()
                    binding.root.snackbar(it.error!!)
                }

                UiState.Loading -> {
                    if (!binding.swipeRefresh.isRefreshing) {
                        binding.examProgress.show()
                    }
                }
                is UiState.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.examProgress.hide()
                    examinationAdapter.items = it.data
                }
            }
        }
    }


    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentExaminationBinding.inflate(inflater, container, false)

    override fun onRefresh() {
        viewModel.getTestsOnline()
    }


}