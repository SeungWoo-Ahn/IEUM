package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.ieum.design_system.icon.DownIcon
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate700
import com.ieum.design_system.theme.Slate800
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.model.calendar.CalendarDate
import com.ieum.presentation.model.calendar.CalendarModel
import com.ieum.presentation.model.calendar.CalendarMonth

@Composable
fun CalendarTopBar(
    modifier: Modifier = Modifier,
    displayedMonth: CalendarMonth,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = screenPadding),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.noRippleClickable(onClick = onClick),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = displayedMonth.toString(),
                style = MaterialTheme.typography.headlineLarge,
            )
            DownIcon()
        }
    }
}

@Composable
fun CalendarWeekDays(
    modifier: Modifier = Modifier,
    firstDayOfWeek: Int,
    weekdays: List<String>,
) {
    val dayNames = mutableListOf<String>()
    for (i in firstDayOfWeek - 1 until weekdays.size) {
        dayNames += weekdays[i]
    }
    for (i in 0 until firstDayOfWeek - 1) {
        dayNames += weekdays[i]
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        dayNames.fastForEach {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = Slate200,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun CalendarMonthsList(
    modifier: Modifier = Modifier,
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
    val firstMonth = remember(yearRange) {
        calendarModel.getMonth(year = yearRange.first, month = 1)
    }

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

    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        state = monthsPagerState,
    ) {
        val month = calendarModel.plusMonths(from = firstMonth, addedMonthsCount = it)
        CalendarMonth(
            month = month,
            today = calendarModel.today,
        )
    }
}

@Composable
private fun CalendarMonth(
    modifier: Modifier = Modifier,
    month: CalendarMonth,
    today: CalendarDate,
) {
    fun isCellInMonth(cellIndex: Int): Boolean {
        val st = month.daysFromStartOfWeekToFirstOfMonth
        val en = st + month.numberOfDays
        return cellIndex in st until en
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        for (weekIndex in 0 until 6) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (dayIndex in 0 until 7) {
                    val cellIndex = weekIndex * 7 + dayIndex
                    if (isCellInMonth(cellIndex)) {
                        val dayOfMonth = cellIndex - month.daysFromStartOfWeekToFirstOfMonth + 1
                        val isToday = CalendarDate(month.year, month.month, dayOfMonth) == today
                        CalendarDay(
                            modifier = Modifier.weight(1f),
                            dayOfMonth = dayOfMonth,
                            isToday = isToday,
                        )

                    } else {
                        CalendarEmptyDay(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarEmptyDay(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.height(70.dp)
    )
}

@Composable
private fun CalendarDay(
    modifier: Modifier = Modifier,
    dayOfMonth: Int,
    isToday: Boolean,
) {
    Box(
        modifier = modifier
            .height(70.dp)
            .background(
                color = White,
                shape = MaterialTheme.shapes.small,
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(all = 2.dp)
                .size(22.dp)
                .background(
                    color = if (isToday) Slate700 else White,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = dayOfMonth.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = if (isToday) White else Slate800,
            )
        }
    }
}