package ma.n1akai.edusync.ui.home.dashboard

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.databinding.FragmentDashboardBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModels()
    private val dashboardAdapter = DashboardAdapter()
    private lateinit var theHomework: Homework

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeworksObserver()
        checkAndUnCheckObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        checkAndUnCheckOListener()

    }

    private fun setUpRecyclerView() {
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
    }

    private fun homeworksObserver() {
        viewModel.dashboardListItemsData.observe(this) {
            when (it) {
                is UiState.Loading -> {
                    binding.dashboardProgress.show()
                }

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

    private fun checkAndUnCheckOListener() {
        dashboardAdapter.onHomeworkCheckedChangedListener =
            object : DashboardAdapter.OnHomeworkCheckedChangedListener {

                override fun onHomeworkCheckedChanged(
                    homework: Homework,
                    view: View,
                    checked: Boolean
                ) {
                    theHomework = homework
                    if (checked) {
                        viewModel.checkHomework(homework.homework_id)
                    } else {
                        viewModel.unCheckHomework(homework.student_homework)
                    }
                }

            }
    }

    private fun checkAndUnCheckObserver() {
        viewModel.updateHomework.observe(this) {
            if (it is UiState.Success) {
                theHomework.student_homework = it.data.id
                binding.root.snackbar(it.data.message)
            } else if (it is UiState.Failure) {
                theHomework.student_homework = -1
                binding.root.snackbar(it.error!!)
            }
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)
}