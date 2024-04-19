package ma.n1akai.edusync.ui.home.homework

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.databinding.FragmentHomeworkBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.ui.home.dashboard.DashboardAdapter
import ma.n1akai.edusync.ui.home.dashboard.DashboardViewModel
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class HomeworkFragment : BaseFragment<FragmentHomeworkBinding>() {

    private val viewModel: HomeworkViewModel by viewModels()
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val homeworksAdapter = HomeworkAdapter()
    private lateinit var theHomework: Homework

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeworkBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndUnCheckObserver()
        dashboardObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        checkAndUnCheckListener()
    }

    private fun setUpRecyclerView() {
        binding.homeworkRcHomeworks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeworksAdapter
        }
    }

    private fun checkAndUnCheckListener() {
        homeworksAdapter.listener = object : DashboardAdapter.OnHomeworkCheckedChangedListener {

            override fun onHomeworkCheckedChanged(
                homework: Homework,
                view: View,
                checked: Boolean
            ) {
                if (checked) {
                    dashboardViewModel.checkHomework(homework.homework_id)
                } else {
                    dashboardViewModel.unCheckHomework(homework.student_homework)
                }
            }

        }
    }

    private fun checkAndUnCheckObserver() {
        dashboardViewModel.updateHomework.observe(this) {
            if (it is UiState.Success) {
                theHomework.student_homework = it.data.id
                binding.root.snackbar(it.data.message)
            } else if (it is UiState.Failure) {
                theHomework.student_homework = -1
                binding.root.snackbar(it.error!!)
            }
        }
    }

    private fun dashboardObserver() {
        viewModel.homeworks.observe(this) {
            when (it) {
                is UiState.Loading -> binding.homeworkProgress.show()

                is UiState.Success -> {
                    binding.homeworkProgress.hide()
                    homeworksAdapter.items = it.data
                }

                is UiState.Failure -> {
                    binding.homeworkProgress.hide()
                    binding.root.snackbar(it.error!!)
                }
            }
        }
    }

}