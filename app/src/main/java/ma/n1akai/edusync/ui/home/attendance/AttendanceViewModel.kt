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

    private val _attendances = MutableLiveData<UiState<List<Any>>>()
    val attendances: LiveData<UiState<List<Any>>>
        get() = _attendances

    init {
        getAttendances()
    }

    fun getAttendances() {
        viewModelScope.safeLaunch({
            _attendances.value = UiState.Loading
            val attendances = repository.getAttendances()
            if (attendances is UiState.Success) {
                val list = mutableListOf<Any>()
                val map = attendances.data.groupBy {
                    it.year
                }
                map.keys.forEach {
                    list.add(Title("Year $it", R.drawable.ic_calendar_blank))
                    list.addAll(map[it]!!)
                }

                _attendances.value = UiState.Success(list)
            }
        }) {
            _attendances.value = UiState.Failure(it)
        }
    }

}