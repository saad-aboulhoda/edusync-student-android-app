package ma.n1akai.edusync.ui.home.examination.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.data.models.TestQuestion
import ma.n1akai.edusync.data.network.responses.BaseResponse
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

    private val _submission = MutableLiveData<UiState<BaseResponse>>()
    val submission: LiveData<UiState<BaseResponse>> get() = _submission

    fun getTestsOnline(id: Int) {
        viewModelScope.safeLaunch({
            _questions.value = UiState.Loading
            _questions.value = studentRepository.getQuestions(id)
        }) {
            _questions.value = UiState.Failure(it)
        }
    }

    fun submitTest(id: Int, map: Map<String, Int>) {
        viewModelScope.safeLaunch({
            _submission.value = UiState.Loading
            _submission.value = studentRepository.submitTest(id,map)
        }) {
            _submission.value = UiState.Failure(it)
        }
    }

}