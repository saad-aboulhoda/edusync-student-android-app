package ma.n1akai.edusync.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import ma.n1akai.edusync.data.repositories.StudentRepository

class LoginViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null
    var loginListener: LoginListener? = null

    fun onLogin(view: View) {
        loginListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            loginListener?.onFailure("Invalid email or password")
        }
        val loginResponse = StudentRepository().login(email!!, password!!)
        loginListener?.onSuccess(loginResponse)
    }

    fun onForgetPassword(view: View) {

    }
}