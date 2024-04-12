package ma.n1akai.edusync.ui.home.feedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Fee
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.data.repository.StudentRepository
import ma.n1akai.edusync.util.UiState
import ma.n1akai.edusync.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class FeeDetailsViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _fees = MutableLiveData<UiState<List<Any>>>()
    val fees: LiveData<UiState<List<Any>>> get() = _fees

    init {
        getFees()
    }

    fun getFees() {
        viewModelScope.safeLaunch({
            _fees.value = UiState.Loading
            val fees = studentRepository.getFees()
            if (fees is UiState.Success) {
                val list = mutableListOf<Any>()
                val map = fees.data.groupBy {
                    it.fee_date.substring(0, 4)
                }
                map.keys.forEach {
                    list.add(Title(it, R.drawable.ic_calendar_blank))
                    list.addAll(map[it]!!)
                }
                _fees.value = UiState.Success(list)
            } else {
                _fees.value = fees
            }
        }) {
            _fees.value = UiState.Failure(it)
        }
    }

}