package ma.n1akai.edusync.ui.auth.forget_password.enter_email

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentEnterEmailBinding
import ma.n1akai.edusync.ui.BaseFragment
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.isValidEmail
import ma.n1akai.edusync.util.navigate
import ma.n1akai.edusync.util.popBackStack
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class EnterEmailFragment : BaseFragment<FragmentEnterEmailBinding>() {

    private val viewModel: EnterEmailViewModel by viewModels()
    private var email: String? = null

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEnterEmailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        generateOtp()
        observer()

        // Cancel Button
        binding.forgetPasswordButtonCancel.popBackStack()
    }

    private fun setTitle() {
        requireActivity().findViewById<TextView>(R.id.auth_text_view_title).text =
            resources.getString(R.string.forget_password)
    }

    private fun validate(): Boolean {
        email = binding.forgetPasswordEditTextEmail.text.toString().trim().lowercase();

        if (!email!!.isValidEmail()) {
            binding.root.snackbar("Email is not valid")
            return false
        }

        return true;
    }

    private fun generateOtp() {
        binding.forgetPasswordButtonGenerateOtp.apply {
            setOnButtonClickListener {
                if (validate()) {
                    viewModel.generateOtp(email!!)
                }
            }
        }
    }

    private fun observer() {
        viewModel.forgetPassword.observe(viewLifecycleOwner) {
            binding.forgetPasswordButtonGenerateOtp.apply {
                when(it) {
                    is UiState.Loading -> {
                        showProgress()
                    }
                    is UiState.Success -> {
                        hideProgress()
                        findNavController()
                            .navigate(EnterEmailFragmentDirections
                                .actionEnterEmailFragmentToEnterOtpFragment(email!!))
                    }
                    is UiState.Failure -> {
                        hideProgress()
                        binding.root.snackbar(it.error!!)
                    }
                }
            }
        }
    }

}