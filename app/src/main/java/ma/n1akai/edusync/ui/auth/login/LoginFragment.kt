package ma.n1akai.edusync.ui.auth.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentLoginBinding
import ma.n1akai.edusync.util.navigate

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

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
    }

    private fun setTitle() {
        activity!!.findViewById<TextView>(R.id.auth_text_view_title).text = "Login"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}