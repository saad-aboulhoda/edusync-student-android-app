package ma.n1akai.edusync.ui.auth.login

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentLoginBinding
import ma.n1akai.edusync.ui.home.HomeActivity
import ma.n1akai.edusync.util.TokenManager
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.isValidEmail
import ma.n1akai.edusync.util.navigate
import ma.n1akai.edusync.util.snackbar
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var tokenManager: TokenManager

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var email: String? = null
    private var password: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        forgetPassword()
        sendLoginInfo()
        observer()
    }

    private fun setTitle() {
        requireActivity().findViewById<TextView>(R.id.auth_text_view_title).text =
            resources.getString(R.string.login)
    }

    private fun forgetPassword() {
        binding.loginButtonForgetPassword
            .navigate(LoginFragmentDirections.actionLoginFragmentToEnterEmailFragment())
    }

    private fun validate() : Boolean {
        binding.apply {
            email = loginEditTextEmail.text.toString().trim().lowercase()
            password = loginEditTextPassword.text.toString()
        }
        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            binding.root.snackbar("Email and Password are required")
            return false
        }
        if (!email!!.isValidEmail()) {
            binding.root.snackbar("Email is not valid")
            return false
        }

        return true
    }

    private fun sendLoginInfo() {
        binding.loginButtonLogin.setOnButtonClickListener {
            if (validate()) {
                viewModel.login(email!!, password!!)
            }
        }
    }

    private fun observer() {
        viewModel.login.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Loading -> {
                    binding.loginButtonLogin.showProgress()
                }
                is UiState.Success -> {
                    binding.loginButtonLogin.hideProgress()

                    binding.root.snackbar(it.data.message!!)

                    // Save Token
                    tokenManager.saveToken(it.data.token!!)

                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finish()

                }
                is UiState.Failure -> {
                    binding.loginButtonLogin.hideProgress()
                    binding.loginButtonLogin.snackbar(it.error!!)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}