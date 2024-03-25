package ma.n1akai.edusync.ui.auth.forget_password.enter_otp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentEnterEmailBinding
import ma.n1akai.edusync.databinding.FragmentEnterOtpBinding
import ma.n1akai.edusync.ui.auth.forget_password.enter_email.EnterEmailFragmentDirections
import ma.n1akai.edusync.util.navigate
import ma.n1akai.edusync.util.popBackStack

class EnterOtpFragment : Fragment() {

    companion object {
        fun newInstance() = EnterOtpFragment()
    }

    private val viewModel: EnterOtpViewModel by viewModels()
    private var _binding: FragmentEnterOtpBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.forgetPasswordButtonCancel.popBackStack()
        binding.forgetPasswordButtonVerify
            .navigate(EnterOtpFragmentDirections.actionEnterOtpFragmentToChangePasswordFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}