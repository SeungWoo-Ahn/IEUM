package com.ieum.presentation.screen.main.home.calendar

import androidx.lifecycle.ViewModel
import com.ieum.presentation.model.calendar.CalendarFilter
import com.ieum.presentation.model.calendar.CalendarMonth
import com.ieum.presentation.model.calendar.createCalendarModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(

) : ViewModel() {
    val calendarModel = createCalendarModel(Locale.KOREA)

    private val _uiState = MutableStateFlow(CalendarUiState.getIdleState(calendarModel.currentMonth))
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    fun onDisplayedMonthChanged(month: CalendarMonth) {
        _uiState.update { it.copy(displayedMonth = month) }
    }

    fun onFilterSelected(filter: CalendarFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }
}