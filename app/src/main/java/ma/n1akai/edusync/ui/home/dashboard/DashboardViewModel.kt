package ma.n1akai.edusync.ui.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _dashboardListItemsData =
        MutableLiveData<UiState<List<Any>>>()
    val dashboardListItemsData: LiveData<UiState<List<Any>>>
        get() = _dashboardListItemsData

    init {
        getDashboardListItems()
    }

    private fun getDashboardListItems() {
        viewModelScope.safeLaunch({
            _dashboardListItemsData.postValue(UiState.Loading)
            val testDiffered = async { studentRepository.getTests() }
            val homeworksDiffered = async { studentRepository.getHomeworks() }

            val test = testDiffered.await()
            val homeworks = homeworksDiffered.await()

            val dashboardList = mutableListOf<Any>()
            if (test is UiState.Success && homeworks is UiState.Success) {
                dashboardList.add(
                    Title(
                        "Latest Tests",
                        R.drawable.ic_calendar_check
                    )
                )
                dashboardList.addAll(test.data)
                dashboardList.add(
                    Title(
                        "Homeworks",
                        R.drawable.ic_pencil
                    )

                )
                dashboardList.addAll(homeworks.data)
                _dashboardListItemsData.postValue(UiState.Success(dashboardList))
            } else {
                _dashboardListItemsData.postValue(UiState.Failure("Something went wrong!"))
            }
        }) {
            _dashboardListItemsData.postValue(UiState.Failure(it))
        }
    }

}