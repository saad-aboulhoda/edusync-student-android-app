package ma.n1akai.edusync.ui.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _tests = MutableLiveData<UiState<List<Test>>>()
    val tests: LiveData<UiState<List<Test>>>
        get() = _tests

    private val _homeworks = MutableLiveData<UiState<List<Homework>>>()
    val homeworks: LiveData<UiState<List<Homework>>>
        get() = _homeworks

    fun getTests() {
        viewModelScope.safeLaunch({
            _tests.value = UiState.Loading
            _tests.value = studentRepository.getTests()
        }) { onError ->
            _tests.value = UiState.Failure(onError)
        }
    }

    fun getHomeworks() {
        viewModelScope.safeLaunch({
            _homeworks.value = UiState.Loading
            _homeworks.value = studentRepository.getHomeworks()
        }) { onError ->
            _homeworks.value = UiState.Failure(onError)
        }
    }

}