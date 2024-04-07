package ma.n1akai.edusync.ui.home.menu

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.FragmentMenuBinding
import ma.n1akai.edusync.ui.auth.AuthActivity
import ma.n1akai.edusync.util.TokenManager
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment(), OnClickListener {

    @Inject
    lateinit var tokenManager: TokenManager

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        directions()
        logout()
    }

    private fun directions() {
        binding.apply {
            menuAttendance.setOnClickListener(this@MenuFragment)
            menuCalendar.setOnClickListener(this@MenuFragment)
            menuExamination.setOnClickListener(this@MenuFragment)
            menuFeeDetails.setOnClickListener(this@MenuFragment)
            menuHomework.setOnClickListener(this@MenuFragment)
            menuMultimedia.setOnClickListener(this@MenuFragment)
            menuReportsCards.setOnClickListener(this@MenuFragment)
            menuProfile.setOnClickListener(this@MenuFragment)
            menuDashboard.setOnClickListener(this@MenuFragment)
        }
    }

    private fun logout() {
        binding.menuLogout.setOnClickListener {
            tokenManager.clearToken()
            startActivity(Intent(requireActivity(), AuthActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        val directionId = when(id) {
            R.id.menu_attendance -> MenuFragmentDirections.actionMenuFragmentToAttendanceFragment()
            R.id.menu_calendar -> MenuFragmentDirections.actionMenuFragmentToCalendarFragment()
            R.id.menu_examination -> MenuFragmentDirections.actionMenuFragmentToExaminationFragment()
            R.id.menu_fee_details -> MenuFragmentDirections.actionMenuFragmentToFeeDetailsFragment()
            R.id.menu_homework -> MenuFragmentDirections.actionMenuFragmentToHomeworkFragment()
            R.id.menu_multimedia -> MenuFragmentDirections.actionMenuFragmentToMultimediaFragment()
            R.id.menu_reports_cards -> MenuFragmentDirections.actionMenuFragmentToReportCardsFragment()
            R.id.menu_profile -> MenuFragmentDirections.actionMenuFragmentToProfileFragment()
            else -> MenuFragmentDirections.actionMenuFragmentToDashboardFragment()
        }
        navController.navigate(directionId)
    }
}