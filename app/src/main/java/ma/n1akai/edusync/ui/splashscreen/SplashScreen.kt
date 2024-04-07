package ma.n1akai.edusync.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusync.R
import ma.n1akai.edusync.ui.auth.AuthActivity
import ma.n1akai.edusync.ui.home.HomeActivity
import ma.n1akai.edusync.util.TokenManager
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent: Intent? = null
        if (!tokenManager.getToken().isNullOrBlank()) {
            intent = Intent(this, HomeActivity::class.java)
        } else {
            intent = Intent(this, AuthActivity::class.java)
        }
        startActivity(intent)
        finish()

    }
}