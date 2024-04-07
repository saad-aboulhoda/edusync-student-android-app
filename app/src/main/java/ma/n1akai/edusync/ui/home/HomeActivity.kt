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
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.databinding.ActivityHomeBinding
import ma.n1akai.edusync.ui.auth.AuthActivity
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
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
        getStudent()
        observer()
    }

    private fun setUpNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setUpToolBar() {
        binding.toolbar.let {
            it.setupWithNavController(navController)
            it.setTitle("ABOULHODA SAAD")
            it.setSubtitle("Class A-01")
        }
    }

    private fun getStudent() {
        viewModel.getStudent()
    }

    private fun observer() {
        viewModel.student.observe(this) {
            binding.root.apply {
                when(it) {
                    is UiState.Loading -> {
                        snackbar("Loading")
                    }
                    is UiState.Success -> {
                        student = it.data
                        val fullName = student.getFullName()
                        binding.toolbar.apply {
                            setTitle(fullName)
                            setSubtitle(student.class_name?.uppercase())
                        }
                        snackbar("Welcome, $fullName")
                    }
                    is UiState.Failure -> {
                        snackbar(it.error!!)
                    }
                }
            }
        }
    }
}