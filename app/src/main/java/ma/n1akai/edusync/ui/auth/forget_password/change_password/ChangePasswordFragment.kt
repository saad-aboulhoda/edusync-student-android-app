package ma.n1akai.edusync.ui.auth.forget_password.change_password

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentChangePasswordBinding
import ma.n1akai.edusync.util.popBackStack

class ChangePasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ChangePasswordFragment()
    }

    private val viewModel: ChangePasswordViewModel by viewModels()
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding: FragmentChangePasswordBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.forgetPasswordButtonCancel.popBackStack()
    }
}