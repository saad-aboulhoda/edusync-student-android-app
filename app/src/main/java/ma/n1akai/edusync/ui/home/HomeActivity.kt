package ma.n1akai.edusync.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding;
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavController()
        setUpToolBar()
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
}