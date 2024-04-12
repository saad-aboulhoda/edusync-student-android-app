package ma.n1akai.edusync.ui.home.examination.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.data.models.TestQuestion
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _questions = MutableLiveData<UiState<List<TestQuestion>>>()
    val questions: LiveData<UiState<List<TestQuestion>>> get() = _questions

    fun getTestsOnline(id: Int) {
        viewModelScope.safeLaunch({
            _questions.value = UiState.Loading
            _questions.value = studentRepository.getQuestions(id)
        }) {
            _questions.value = UiState.Failure(it)
        }
    }

}