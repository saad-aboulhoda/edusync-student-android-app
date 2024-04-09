package ma.n1akai.edusync.ui.auth.forget_password.enter_email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ma.n1akai.edusync.data.network.responses.ForgetPasswordResponse
import ma.n1akai.edusync.data.repository.AuthRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class EnterEmailViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _forgetPassword = MutableLiveData<UiState<ForgetPasswordResponse>>()
    val forgetPassword: LiveData<UiState<ForgetPasswordResponse>>
        get() = _forgetPassword

    fun generateOtp(email: String) {
        viewModelScope.safeLaunch({
            _forgetPassword.value = UiState.Loading
            _forgetPassword.value = repository.generateOtp(email)
        }) {
            _forgetPassword.value = UiState.Failure(it)
        }
    }

}