package ma.n1akai.edusync.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.User
import ma.n1akai.edusync.data.network.Api
import ma.n1akai.edusync.data.network.NetworkConnectionInterceptor
import ma.n1akai.edusync.data.repositories.StudentRepository
import ma.n1akai.edusync.databinding.ActivityLoginBinding
import ma.n1akai.edusync.util.snackbar
import ma.n1akai.edusync.util.toast

class LoginActivity : AppCompatActivity(), LoginListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
    }

    private fun bind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val api = Api(NetworkConnectionInterceptor(this))
        val repository = StudentRepository(api)
        val factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        binding.viewmodel = viewModel
        viewModel.loginListener = this
    }

    override fun onStarted() {
        binding.rootLayout.snackbar("Started");
    }

    override fun onSuccess(user: User) {
        binding.rootLayout.snackbar("Welcome ${user.lastName} ${user.firstName}")
    }

    override fun onFailure(msg: String) {
        binding.rootLayout.snackbar(msg);
    }
}