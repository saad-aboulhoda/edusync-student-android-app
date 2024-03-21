package ma.n1akai.edusync.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ma.n1akai.edusync.R
import ma.n1akai.edusync.databinding.ActivityLoginBinding
import ma.n1akai.edusync.util.toast

class LoginActivity : AppCompatActivity(), LoginListener {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
    }

    fun bind() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewmodel = viewModel
        viewModel.loginListener = this
    }

    override fun onStarted() {
        toast("Started");
    }

    override fun onSuccess(loginResponse: LiveData<String>) {
        loginResponse.observe(this) {
            toast(it);
        }
    }

    override fun onFailure(msg: String) {
        toast(msg);
    }
}