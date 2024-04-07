package ma.n1akai.edusync.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.data.repository.AuthRepository
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.TokenManager
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _student = MutableLiveData<UiState<Student>>()
    val student: LiveData<UiState<Student>>
        get() = _student

    fun getStudent() {
        viewModelScope.safeLaunch({
            _student.value = UiState.Loading
            repository.getStudent {
                _student.value = it
            }
        }) { onError ->
            _student.value = UiState.Failure(onError)
        }
    }

}