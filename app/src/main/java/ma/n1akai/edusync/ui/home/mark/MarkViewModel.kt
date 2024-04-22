package ma.n1akai.edusync.ui.home.mark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class MarkViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _tests = MutableLiveData<UiState<List<Any>>>()
    val tests: LiveData<UiState<List<Any>>> get() = _tests

    init {
        getTests()
    }

    fun getTests() {
        viewModelScope.safeLaunch({
            _tests.postValue(UiState.Loading)
            val tests = studentRepository.getTests()
            if (tests is UiState.Success) {
                val testsList = mutableListOf<Any>()
                val groupedTests = tests.data.groupBy {
                    "${it.course_code} - ${it.course_name}"
                }
                groupedTests.keys.forEach {
                    testsList.add(
                        Title(
                            it, R.drawable.ic_pencil
                        )
                    )
                    testsList.addAll(groupedTests[it]!!)
                }
                _tests.postValue(UiState.Success(testsList))
            }

        }) {
            _tests.postValue(UiState.Failure(it))
        }
    }

}