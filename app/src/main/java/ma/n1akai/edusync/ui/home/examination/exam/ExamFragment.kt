package ma.n1akai.edusync.ui.home.examination.exam

import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.TestOnline
import ma.n1akai.edusync.databinding.FragmentExamBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.snackbar
import java.text.SimpleDateFormat

@AndroidEntryPoint
class ExamFragment : BaseFragment<FragmentExamBinding>() {

    private val viewModel: ExamViewModel by viewModels()
    private val examAdapter = ExamAdapter()
    private lateinit var testOnline: TestOnline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            testOnline = requireArguments().getSerializable("testOnline", TestOnline::class.java)!!
        } else {
            testOnline = requireArguments().getSerializable("testOnline") as TestOnline
        }

        viewModel.getTestsOnline(testOnline.test_online_id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            examTvCourse.text = testOnline.course_name
            examTvDuration.text = testOnline.duration
        }

        binding.examRcQuestions.apply {
            adapter = examAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }
        viewModel.questions.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    binding.examProgress.hide()
                    binding.root.snackbar(it.error!!)
                }

                UiState.Loading -> binding.examProgress.show()
                is UiState.Success -> {
                    binding.examProgress.hide()
                    examAdapter.items = it.data
                    startTimer()
                }
            }
        }
    }

    private fun startTimer() {
        val formater = SimpleDateFormat("HH:mm:ss")
        val date = formater.parse(testOnline.duration)
        object : CountDownTimer(date!!.time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.examTvDuration.text = formater.format(millisUntilFinished)
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }.start()
    }

    private fun submit() {

    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentExamBinding.inflate(inflater, container, false)
}