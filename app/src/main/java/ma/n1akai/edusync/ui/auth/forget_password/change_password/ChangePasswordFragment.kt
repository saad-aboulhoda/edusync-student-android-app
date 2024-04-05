package ma.n1akai.edusync.ui.auth.forget_password.change_password

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentChangePasswordBinding
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.popBackStack
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private val viewModel: ChangePasswordViewModel by viewModels()
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding: FragmentChangePasswordBinding get() = _binding!!
    private var email: String? = null
    private var otp: String? = null
    private lateinit var newPassword: String
    private lateinit var confirmNewPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args()
        changePassword()
        observer()

        binding.forgetPasswordButtonCancel.popBackStack()
    }

    private fun args() {
        email = arguments?.getString("email")
        otp = arguments?.getString("otp")
    }

    private fun validate(): Boolean {

        binding.apply {
            newPassword = changePasswordEditNewPassword.text.toString()
            confirmNewPassword = changePasswordEditConfirmPassword.text.toString()
        }

        if (newPassword.isBlank() || confirmNewPassword.isBlank()) {
            binding.root.snackbar("Password and Confirm Password are required")
            return false
        }

        if (newPassword != confirmNewPassword) {
            binding.root.snackbar("Password and Confirm Password do not match")
            return false
        }

        if (newPassword.length < 8) {
            binding.root.snackbar("Password can't be less than 8 characters")
            return false
        }

        return true
    }

    private fun changePassword() {
        binding.changePasswordButtonSubmit.setOnButtonClickListener {
            if (validate()) {
                viewModel.changePassword(email!!, otp!!, newPassword)
            }
        }
    }

    private fun observer() {
        viewModel.changePassword.observe(viewLifecycleOwner) {
            binding.changePasswordButtonSubmit.apply {
                when(it) {
                    is UiState.Loading -> {
                        showProgress()
                    }
                    is UiState.Success -> {
                        hideProgress()
                        snackbar("Password changed successfully, you can login now")
                        findNavController()
                            .navigate(ChangePasswordFragmentDirections
                                .actionGlobalLoginFragment())
                    }
                    is UiState.Failure -> {
                        hideProgress()
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