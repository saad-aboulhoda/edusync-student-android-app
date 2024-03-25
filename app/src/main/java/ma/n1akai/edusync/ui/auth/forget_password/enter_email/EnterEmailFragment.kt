package ma.n1akai.edusync.ui.auth.forget_password.enter_email

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentEnterEmailBinding
import ma.n1akai.edusync.util.navigate
import ma.n1akai.edusync.util.popBackStack

class EnterEmailFragment : Fragment() {

    companion object {
        fun newInstance() = EnterEmailFragment()
    }

    private val viewModel: EnterEmailViewModel by viewModels()
    private var _binding: FragmentEnterEmailBinding? = null
    private val binding: FragmentEnterEmailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        binding.forgetPasswordButtonCancel.popBackStack()
        binding.forgetPasswordButtonGenerateOtp
            .navigate(EnterEmailFragmentDirections.actionEnterEmailFragmentToEnterOtpFragment())
    }

    private fun setTitle() {
        activity!!.findViewById<TextView>(R.id.auth_text_view_title).text = "Forget Password"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}