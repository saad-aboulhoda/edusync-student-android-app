package ma.n1akai.edusync.ui.home.dashboard

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentDashboardBinding
import ma.n1akai.edusync.ui.adapters.HomeworksAdapter
import ma.n1akai.edusync.ui.adapters.TestsAdapter
import ma.n1akai.edusync.ui.home.HomeActivity
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val testsAdapter = TestsAdapter()
    private val homeworksAdapter = HomeworksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTests()
        setUpHomeworks()
    }

    private fun setUpTests() {
        binding.dashboardRcNotes.adapter = testsAdapter
        viewModel.getTests()
        viewModel.tests.observe(viewLifecycleOwner) {
            binding.root.apply {
                when (it) {
                    is UiState.Loading -> {

                    }

                    is UiState.Success -> {
                        testsAdapter.tests = it.data
                    }

                    is UiState.Failure -> {
                        snackbar(it.error!!)
                    }
                }
            }
        }
    }

    private fun onCheckedChange() {
        homeworksAdapter.setOnCheckedChangeListener(object : HomeworksAdapter.CheckedChangedListener {
            override fun onCheckedChange(homeworkId: String, checked: Boolean) {

            }
        })
    }

    private fun setUpHomeworks() {
        onCheckedChange()
        binding.dashboardRcHomeworks.adapter = homeworksAdapter
        viewModel.getHomeworks()
        viewModel.homeworks.observe(viewLifecycleOwner) {
            binding.root.apply {
                when (it) {
                    is UiState.Loading -> {

                    }

                    is UiState.Success -> {
                        homeworksAdapter.homeworks = it.data
                    }

                    is UiState.Failure -> {
                        snackbar(it.error!!)
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}