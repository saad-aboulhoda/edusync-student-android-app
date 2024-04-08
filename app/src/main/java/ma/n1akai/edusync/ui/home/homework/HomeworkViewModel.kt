package ma.n1akai.edusync.ui.home.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _homeworks = MutableLiveData<UiState<List<Any>>>()
    val homeworks: LiveData<UiState<List<Any>>> get() = _homeworks

    init {
        getHomeworks()
    }

    fun getHomeworks() {
        viewModelScope.safeLaunch({
            _homeworks.postValue(UiState.Loading)
            val hws = studentRepository.getHomeworks()
            if (hws is UiState.Success) {
                val homeworksList = mutableListOf<Any>()
                val sortedHws =
                    hws.data.sortedByDescending { it.created_at }
                val groupedHws = sortedHws.groupBy {
                    it.created_at.substring(0, min(it.created_at.length, 10))
                }
                groupedHws.keys.forEach {
                    homeworksList.add(
                        Title(
                            it, R.drawable.ic_pencil
                        )
                    )
                    homeworksList.addAll(groupedHws[it]!!)
                }
                _homeworks.postValue(UiState.Success(homeworksList))
            }

        }) {
            _homeworks.postValue(UiState.Failure(it))
        }
    }

}