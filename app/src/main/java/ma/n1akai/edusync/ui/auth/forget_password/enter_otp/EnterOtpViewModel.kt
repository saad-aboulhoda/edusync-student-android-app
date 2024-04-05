package ma.n1akai.edusync.ui.auth.forget_password.enter_otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ma.n1akai.edusync.data.repository.AuthRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class EnterOtpViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _verifyOtp = MutableLiveData<UiState<String>>()
    val verifyOtp: LiveData<UiState<String>>
        get() = _verifyOtp

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.safeLaunch({
            _verifyOtp.value = UiState.Loading
            repository.verifyOtp(email, otp) {
                _verifyOtp.value = it
            }
        }) {
            _verifyOtp.value = UiState.Failure(it)
        }
    }

}