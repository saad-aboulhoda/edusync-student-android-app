package ma.n1akai.edusync.ui.home.examination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.TestOnline
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class ExaminationViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _tests = MutableLiveData<UiState<List<Any>>>()
    val tests: LiveData<UiState<List<Any>>> get() = _tests

    fun getTestsOnline() {
        viewModelScope.safeLaunch({
            _tests.value = UiState.Loading
            val testsOnline = studentRepository.getTestsOnline()
            if (testsOnline is UiState.Success) {
                val map = testsOnline.data.groupBy {
                    it.course_name
                }
                val list = mutableListOf<Any>()
                map.keys.forEach {
                    list.add(Title(it, R.drawable.ic_calendar_check))
                    list.addAll(map[it]!!)
                }
                _tests.value = UiState.Success(list)
            } else if(testsOnline is UiState.Failure) {
                _tests.value = testsOnline
            }
        }) {
            _tests.value = UiState.Failure(it)
        }
    }

}