package ma.n1akai.edusync.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import ma.n1akai.edusync.data.repositories.StudentRepository
import ma.n1akai.edusync.util.ApiException
import ma.n1akai.edusync.util.Coroutines
import ma.n1akai.edusync.util.NoInternetException

class LoginViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var loginListener: LoginListener? = null

    fun onLogin(view: View) {
        loginListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            loginListener?.onFailure("Invalid email or password")
        }
        Coroutines.main {
            try {
                val authResponse = repository.login(email!!, password!!)
                authResponse.user?.let {
                    loginListener?.onSuccess(it)
                    return@main
                }
                loginListener?.onFailure(authResponse.message!!)

            } catch (e: ApiException) {
                loginListener?.onFailure(e.message.toString())
            } catch (e: NoInternetException) {
                loginListener?.onFailure(e.message.toString())
            }

        }

    }

    fun onForgetPassword(view: View) {

    }
}