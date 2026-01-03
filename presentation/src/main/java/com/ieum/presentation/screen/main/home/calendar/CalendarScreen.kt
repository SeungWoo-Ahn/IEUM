package com.ieum.presentation.screen.main.home.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ieum.design_system.theme.Slate50
import com.ieum.presentation.model.calendar.CalendarModel
import com.ieum.presentation.model.calendar.CalendarMonth
import com.ieum.presentation.model.calendar.createCalendarModel
import com.ieum.presentation.screen.component.CalendarMonthsList
import com.ieum.presentation.screen.component.CalendarTopBar
import com.ieum.presentation.screen.component.CalendarWeekDays
import java.util.Locale

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
) {
    val calendarModel = remember { createCalendarModel(Locale.KOREA) }
    var displayedMonth by remember {
        val today = calendarModel.today
        mutableStateOf(calendarModel.getMonth(today.year, today.month))
    }
    CalendarScreen(
        modifier = modifier,
        calendarModel = calendarModel,
        displayedMonth = displayedMonth,
        onDisplayedMonthChanged = { displayedMonth = it }
    )
}

@Composable
private fun CalendarScreen(
    modifier: Modifier,
    calendarModel: CalendarModel,
    displayedMonth: CalendarMonth,
    onDisplayedMonthChanged: (CalendarMonth) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Slate50),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CalendarTopBar(
            displayedMonth = displayedMonth,
            onClick = {}
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            CalendarWeekDays(
                firstDayOfWeek = calendarModel.firstDayOfWeek,
                weekdays = calendarModel.weekdayNames,
            )
            CalendarMonthsList(
                calendarModel = calendarModel,
                displayedMonth = displayedMonth,
                onDisplayedMonthChanged = onDisplayedMonthChanged,
            )
        }
    }
}