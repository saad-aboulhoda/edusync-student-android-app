package ma.n1akai.edusync.ui.auth

import androidx.lifecycle.LiveData

interface LoginListener {
    fun onStarted();
    fun onSuccess(loginResponse: LiveData<String>);
    fun onFailure(msg: String)
}