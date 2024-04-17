package ma.n1akai.edusync.ui.home.examination.exam

import android.app.AlertDialog
import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.TestOnline
import ma.n1akai.edusync.databinding.FragmentExamBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.hide
import ma.n1akai.edusync.util.show
import ma.n1akai.edusync.util.snackbar
import java.text.SimpleDateFormat

@AndroidEntryPoint
class ExamFragment : BaseFragment<FragmentExamBinding>() {

    private val viewModel: ExamViewModel by viewModels()
    private val examAdapter = ExamAdapter()
    private lateinit var testOnline: TestOnline
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var toolbar: MaterialToolbar
    private var time: Long = 0

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (time > 0) {
                showSubmissionDialog()
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpToolbar()
        activity?.onBackPressedDispatcher?.addCallback(this, onBackPressedCallback)

        getTestOnline()
        viewModel.getTestsOnline(testOnline.test_online_id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observer()

        binding.examBtnSubmit.setOnButtonClickListener {
            submit()
        }
    }

    private fun getTestOnline() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            testOnline = requireArguments().getSerializable("testOnline", TestOnline::class.java)!!
        } else {
            testOnline = requireArguments().getSerializable("testOnline") as TestOnline
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            examTvCourse.text = testOnline.course_name
            examTvDuration.text = testOnline.duration
        }

        binding.examRcQuestions.apply {
            adapter = examAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun startTimer() {
        val formater = SimpleDateFormat("HH:mm:ss")
        val date = formater.parse(testOnline.duration)
        countDownTimer = object : CountDownTimer(date!!.time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished
                binding.examTvDuration.text = formater.format(time)
            }

            override fun onFinish() {
                submit()
            }

        }.start()
    }

    private fun observer() {
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

    private fun setUpToolbar() {
        toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedCallback.handleOnBackPressed()
        }
    }

    private fun submit() {
        viewModel.submitTest(testOnline.test_online_id, examAdapter.data)
        viewModel.submission.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    binding.examBtnSubmit.hide()
                    binding.root.snackbar(it.error!!)
                }

                UiState.Loading -> binding.examBtnSubmit.show()
                is UiState.Success -> {
                    binding.examBtnSubmit.hide()
                    binding.root.snackbar(it.data.message)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun showSubmissionDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Submit Test?")
            .setMessage("If you leave now, your answers will be submitted")
            .setPositiveButton("Submit") { dialog, _ ->
                submit()
                dialog.dismiss()
                onBackPressedCallback.isEnabled = false
                findNavController().navigateUp()
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
        onBackPressedCallback.remove()
        toolbar.setNavigationOnClickListener(null)
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentExamBinding.inflate(inflater, container, false)
}