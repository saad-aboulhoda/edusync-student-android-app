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
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.navigate
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
        binding.loginButtonForgetPassword
            .navigate(LoginFragmentDirections.actionLoginFragmentToEnterEmailFragment())
        binding.loginButtonLogin.setOnClickListener {
            binding.apply {
                viewModel
                    .login(loginEditTextEmail.text.toString(), loginEditTextPassword.text.toString())
            }
        }
        viewModel.login.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Loading -> {
                    binding.loginButtonLogin.snackbar("Loading")
                }
                is UiState.Success -> {
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finish();
                }
                is UiState.Failure -> {
                    binding.loginButtonLogin.snackbar(it.error!!)
                }
            }
        }
    }

    private fun setTitle() {
        requireActivity().findViewById<TextView>(R.id.auth_text_view_title).text =
            resources.getString(R.string.login)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}