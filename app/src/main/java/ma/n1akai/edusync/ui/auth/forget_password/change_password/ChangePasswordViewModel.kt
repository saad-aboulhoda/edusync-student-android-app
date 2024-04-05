package ma.n1akai.edusync.ui.auth.forget_password.change_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.data.repository.AuthRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _changePassword = MutableLiveData<UiState<String>>()
    val changePassword: LiveData<UiState<String>>
        get() = _changePassword

    fun changePassword(email: String, otp: String, password: String) {
        viewModelScope.safeLaunch({
            _changePassword.value = UiState.Loading
            repository.changePassword(email, otp, password) {
                _changePassword.value = it
            }
        }) {
            _changePassword.value = UiState.Failure(it)
        }
    }
}