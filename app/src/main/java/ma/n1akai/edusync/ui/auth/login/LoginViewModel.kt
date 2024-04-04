package ma.n1akai.edusync.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ma.n1akai.edusync.data.repository.AuthRepository
import ma.n1akai.edusync.util.UiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _login = MutableLiveData<UiState<String>>();
    val login: LiveData<UiState<String>>
        get() = _login

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _login.value = UiState.Loading
             repository.login(email, password) {
                 _login.value = it
            }
        }
    }

}