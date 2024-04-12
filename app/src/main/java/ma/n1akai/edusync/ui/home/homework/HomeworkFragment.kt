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
import ma.n1akai.edusync.databinding.FragmentHomeworkBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class HomeworkFragment : BaseFragment<FragmentHomeworkBinding>() {

    private val viewModel: HomeworkViewModel by viewModels()
    private val homeworksAdapter = HomeworkAdapter()

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeworkBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeworkRcHomeworks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeworksAdapter
        }

        viewModel.homeworks.observe(viewLifecycleOwner) {
            when(it) {
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