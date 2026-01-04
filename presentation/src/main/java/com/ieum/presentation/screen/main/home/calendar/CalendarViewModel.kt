package com.ieum.presentation.screen.main.home.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.domain.model.post.Post
import com.ieum.domain.usecase.user.GetMyWellnessListByMonthUseCase
import com.ieum.presentation.mapper.toCalendarUiModel
import com.ieum.presentation.model.calendar.CalendarFilter
import com.ieum.presentation.model.calendar.CalendarMonth
import com.ieum.presentation.model.calendar.createCalendarModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getMyWellnessListByMonthUseCase: GetMyWellnessListByMonthUseCase,
) : ViewModel() {
    val calendarModel = createCalendarModel(Locale.KOREA)

    private val _uiState = MutableStateFlow(CalendarUiState.getIdleState(calendarModel.currentMonth))
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    fun onDisplayedMonthChanged(month: CalendarMonth) {
        _uiState.update { it.copy(displayedMonth = month) }
        getMyWellnessListByMonth()
    }

    fun onFilterSelected(filter: CalendarFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    private fun getMyWellnessListByMonth() {
        viewModelScope.launch {
            val (fromDate, toDate) = uiState.value.displayedMonth.getDateRange()
            getMyWellnessListByMonthUseCase(fromDate, toDate)
                .onSuccess { wellnessList ->
                    _uiState.update {
                        it.copy(
                            wellnessList = wellnessList.map(Post.Wellness::toCalendarUiModel)
                        )
                    }
                }
                .onFailure {
                    // 데이터 로드 실패
                }
        }
    }
}