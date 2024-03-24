package ma.n1akai.edusync.ui.auth

import androidx.lifecycle.LiveData
import ma.n1akai.edusync.data.models.User

interface LoginListener {
    fun onStarted();
    fun onSuccess(user: User);
    fun onFailure(msg: String)
}