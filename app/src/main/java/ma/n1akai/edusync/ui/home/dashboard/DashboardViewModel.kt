package ma.n1akai.edusync.ui.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.data.network.responses.BaseResponse
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.ResourcesProvider
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private val _dashboardListItemsData =
        MutableLiveData<UiState<List<Any>>>()
    val dashboardListItemsData: LiveData<UiState<List<Any>>>
        get() = _dashboardListItemsData

    private val _updateHomework = MutableLiveData<UiState<BaseResponse>>()
    val updateHomework: LiveData<UiState<BaseResponse>>
        get() = _updateHomework

    init {
        getDashboardListItems()
    }

    fun checkHomework(homeworkId: Int) {
        viewModelScope.safeLaunch({
            _updateHomework.value = UiState.Loading
            _updateHomework.value = studentRepository.checkHomework(homeworkId)
        }) {
            _updateHomework.value = UiState.Failure(it)
        }
    }

    fun unCheckHomework(studentHomework: Int) {
        viewModelScope.safeLaunch({
            _updateHomework.value = UiState.Loading
            _updateHomework.value = studentRepository.unCheckHomework(studentHomework)
        }) {
            _updateHomework.value = UiState.Failure(it)
        }
    }

    fun getDashboardListItems() {
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
                        resourcesProvider.getString(R.string.latest_tests),
                        R.drawable.ic_calendar_check,
                        DashboardFragmentDirections.actionDashboardFragmentToMarkFragment()
                    )
                )
                if (test.data.size > 4) {
                    dashboardList.addAll(test.data.subList(0, 4))
                } else {
                    dashboardList.addAll(test.data)
                }

                dashboardList.add(
                    Title(
                        resourcesProvider.getString(R.string.homeworks),
                        R.drawable.ic_pencil,
                        DashboardFragmentDirections.actionDashboardFragmentToHomeworkFragment()
                    )

                )

                if (homeworks.data.size > 4) {
                    dashboardList.addAll(homeworks.data.subList(0, 8))
                } else {
                    dashboardList.addAll(homeworks.data)
                }
                _dashboardListItemsData.postValue(UiState.Success(dashboardList))
            } else if (test is UiState.Failure) {
                _dashboardListItemsData.postValue(UiState.Failure(test.error))
            }
        }) {
            _dashboardListItemsData.postValue(UiState.Failure(it))
        }
    }

}