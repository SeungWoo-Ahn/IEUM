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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ieum.design_system.theme.Slate50
import com.ieum.presentation.model.calendar.CalendarFilter
import com.ieum.presentation.model.calendar.CalendarModel
import com.ieum.presentation.model.calendar.CalendarMonth
import com.ieum.presentation.screen.component.CalendarFilterArea
import com.ieum.presentation.screen.component.CalendarMonthsList
import com.ieum.presentation.screen.component.CalendarTopBar
import com.ieum.presentation.screen.component.CalendarWeekDays
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalendarScreen(
        modifier = modifier,
        scope = scope,
        uiState = uiState,
        calendarModel = viewModel.calendarModel,
        onDisplayedMonthChanged = viewModel::onDisplayedMonthChanged,
        onFilterSelected = viewModel::onFilterSelected,
    )
}

@Composable
private fun CalendarScreen(
    modifier: Modifier,
    scope: CoroutineScope,
    uiState: CalendarUiState,
    calendarModel: CalendarModel,
    onDisplayedMonthChanged: (CalendarMonth) -> Unit,
    onFilterSelected: (CalendarFilter) -> Unit,
) {
    fun numberOfMonthsInRange(yearRange: IntRange) =
        (yearRange.last - yearRange.first + 1) * 12

    val monthIndex =
        uiState.displayedMonth.indexIn(calendarModel.yearRange).coerceAtLeast(0)

    val monthsPagerState = rememberPagerState(
        initialPage = monthIndex,
        pageCount = { numberOfMonthsInRange(calendarModel.yearRange) }
    )

    LaunchedEffect(monthsPagerState) {
        snapshotFlow { monthsPagerState.currentPage }
            .collect { index ->
                val yearOffset = index / 12
                val month = index % 12 + 1
                onDisplayedMonthChanged(
                    calendarModel.getMonth(
                        year = calendarModel.yearRange.first + yearOffset,
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
            displayedMonth = uiState.displayedMonth,
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
            CalendarFilterArea(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = onFilterSelected,
            )
            CalendarWeekDays()
            CalendarMonthsList(
                selectedFilter = uiState.selectedFilter,
                uiStateByDayOfMonth = uiState.dateUiStateByDayOfMonth,
                calendarModel = calendarModel,
                monthsPagerState = monthsPagerState,
            )
        }
    }
}