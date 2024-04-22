package ma.n1akai.edusync.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.databinding.ActivityHomeBinding
import ma.n1akai.edusync.ui.auth.AuthActivity
import ma.n1akai.edusync.ui.home.dashboard.DashboardFragmentDirections
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.hide
import ma.n1akai.edusync.util.safeLaunch
import ma.n1akai.edusync.util.show
import ma.n1akai.edusync.util.snackbar

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: HomeViewModel
    lateinit var student: Student


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setUpNavController()
        setUpToolBar()
        destinationUi()
        getStudent()
        observer()
        goToProfile()
    }

    private fun setUpNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setUpToolBar() {
        binding.toolbar.setupWithNavController(navController)
    }

    private fun getStudent() {
        viewModel.getStudent()
    }

    private fun observer() {
        viewModel.student.observe(this) {
            binding.root.apply {
                when (it) {
                    is UiState.Loading -> {
                        binding.homeLoadingLayout.show()
                    }

                    is UiState.Success -> {
                        binding.homeLoadingLayout.hide()
                        student = it.data
                        setUpToolbarTitleSubTitleLogo()
                    }

                    is UiState.Failure -> {
                        binding.homeLoadingLayout.hide()
                        snackbar(it.error!!)
                    }
                }
            }
        }
    }

    private fun destinationUi() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val id = destination.id
            when (id) {
                R.id.menuFragment -> {
                    binding.toolbar.apply {
                        setLogo(null)
                        setSubtitle(null)
                        setTitle(null)
                        setNavigationIcon(R.drawable.ic_close)
                        binding.homeIvProfile.show()
                    }
                }

                R.id.profileFragment,
                R.id.markFragment,
                R.id.homeworkFragment -> {
                    binding.toolbar.apply {
                        setLogo(null)
                        setSubtitle(null)
                        binding.homeIvProfile.hide()
                    }
                }

                R.id.dashboardFragment -> {
                    setUpToolbarTitleSubTitleLogo()
                    binding.homeIvProfile.show()
                }

                else -> binding.homeIvProfile.show()
            }
        }
    }

    private fun setUpToolbarTitleSubTitleLogo() {
        if (this@HomeActivity::student.isInitialized) {
            binding.toolbar.apply {
                val fullName = student.getFullName()
                setLogo(R.drawable.ic_bento_menu)
                setTitle(fullName)
                setSubtitle(student.class_name!!.uppercase())

                getChildAt(2).setOnClickListener {
                    navController
                        .navigate(R.id.action_dashboardFragment_to_menuFragment)
                }
            }
        }
    }

    private fun goToProfile() {
        binding.homeIvProfile.setOnClickListener {
            navController
                .navigate(R.id.action_global_profileFragment)
        }
    }
}