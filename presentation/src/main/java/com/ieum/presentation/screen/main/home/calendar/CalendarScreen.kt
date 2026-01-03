package com.ieum.presentation.screen.main.home.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ieum.design_system.theme.Slate50
import com.ieum.presentation.model.calendar.CalendarModel
import com.ieum.presentation.model.calendar.CalendarMonth
import com.ieum.presentation.model.calendar.createCalendarModel
import com.ieum.presentation.screen.component.CalendarMonthsList
import com.ieum.presentation.screen.component.CalendarTopBar
import com.ieum.presentation.screen.component.CalendarWeekDays
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
) {
    val calendarModel = remember { createCalendarModel(Locale.KOREA) }
    var displayedMonth by remember {
        val today = calendarModel.today
        mutableStateOf(calendarModel.getMonth(today.year, today.month))
    }
    CalendarScreen(
        modifier = modifier,
        scope = scope,
        calendarModel = calendarModel,
        displayedMonth = displayedMonth,
        onDisplayedMonthChanged = { displayedMonth = it }
    )
}

@Composable
private fun CalendarScreen(
    modifier: Modifier,
    scope: CoroutineScope,
    calendarModel: CalendarModel,
    displayedMonth: CalendarMonth,
    yearRange: IntRange = IntRange(2024, 2026),
    onDisplayedMonthChanged: (CalendarMonth) -> Unit,
) {
    fun numberOfMonthsInRange(yearRange: IntRange) =
        (yearRange.last - yearRange.first + 1) * 12

    val monthIndex = displayedMonth.indexIn(yearRange).coerceAtLeast(0)

    val monthsPagerState = rememberPagerState(
        initialPage = monthIndex,
        pageCount = { numberOfMonthsInRange(yearRange) }
    )

    LaunchedEffect(monthsPagerState) {
        snapshotFlow { monthsPagerState.currentPage }
            .collect { index ->
                val yearOffset = index / 12
                val month = index % 12 + 1
                onDisplayedMonthChanged(
                    calendarModel.getMonth(
                        year = yearRange.first + yearOffset,
                        month = month
                    )
                )
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Slate50),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CalendarTopBar(
            displayedMonth = displayedMonth,
            prevEnabled = monthsPagerState.canScrollBackward,
            nextEnabled = monthsPagerState.canScrollForward,
            onPrev = { scope.launch {
                monthsPagerState.animateScrollToPage(monthsPagerState.currentPage - 1)
            } },
            onNext = { scope.launch {
                monthsPagerState.animateScrollToPage(monthsPagerState.currentPage + 1)
            } },
            onClick = {},
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            CalendarWeekDays()
            CalendarMonthsList(
                calendarModel = calendarModel,
                yearRange = yearRange,
                monthsPagerState = monthsPagerState,
            )
        }
    }
}