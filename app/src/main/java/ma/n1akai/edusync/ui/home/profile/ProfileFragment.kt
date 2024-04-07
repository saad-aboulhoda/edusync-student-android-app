package ma.n1akai.edusync.ui.home.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.databinding.FragmentProfileBinding
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.hide
import ma.n1akai.edusync.util.show
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getStudent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
    }

    private fun observer() {
        viewModel.student.observe(viewLifecycleOwner) {
            binding.apply {
                when(it) {
                    is UiState.Loading -> {
                        profileLoadingLayout.show()
                    }
                    is UiState.Success -> {
                        profileLoadingLayout.hide()
                        student = it.data
                        dataToUi()
                    }
                    is UiState.Failure -> {
                        profileLoadingLayout.hide()
                        root.snackbar(it.error!!)
                    }
                }
            }
        }
    }

    private fun dataToUi() {
        binding.apply {
            profileTvFullName.text = student.getFullName()
            profileTvClass.text = student.class_name!!.uppercase()
            profileTvRollNumber.text = student.student_id.toString()
            profileTvDateOfBirth.text = student.date_of_birth
            profileTvEmergencyContact.text = student.phone_number
            profileTvFathersName.text = student.fathers_name
            profileTvMothersName.text = student.mothers_name
        }
    }
}