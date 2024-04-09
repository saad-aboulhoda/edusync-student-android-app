package ma.n1akai.edusync.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.data.models.Student
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _student = MutableLiveData<UiState<Student>>()
    val student: LiveData<UiState<Student>> get() = _student

    fun getStudent() {
        viewModelScope.safeLaunch({
            _student.value = UiState.Loading
            _student.value = studentRepository.getStudent()
        }) { error ->
            _student.value = UiState.Failure(error)
        }
    }

}