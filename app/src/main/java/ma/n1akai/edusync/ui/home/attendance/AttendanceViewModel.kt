package ma.n1akai.edusync.ui.home.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Attendance
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _absents = MutableLiveData<UiState<List<Any>>>()
    val absents: LiveData<UiState<List<Any>>>
        get() = _absents

    init {
        getAttendances()
    }

    fun getAttendances() {
        viewModelScope.safeLaunch({
            _absents.value = UiState.Loading
            val absents = repository.getAbsents()
            if (absents is UiState.Success) {
                val list = mutableListOf<Any>()
                val map = absents.data.groupBy {
                    it.date
                }

                map.keys.forEach {
                    list.add(Title(it, R.drawable.ic_calendar_blank))
                    list.addAll(map[it]!!)
                }

                _absents.value = UiState.Success(list)
            }
        }) {
            _absents.value = UiState.Failure(it)
        }
    }

}