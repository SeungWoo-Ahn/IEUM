package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.ieum.design_system.icon.LeftIcon
import com.ieum.design_system.icon.RightIcon
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate500
import com.ieum.design_system.theme.Slate700
import com.ieum.design_system.theme.Slate800
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.Slate950
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.model.calendar.CalendarFilter
import com.ieum.presentation.model.calendar.CalendarModel
import com.ieum.presentation.model.calendar.CalendarMonth
import com.ieum.presentation.model.calendar.CalendarWeekDays

@Composable
fun CalendarTopBar(
    modifier: Modifier = Modifier,
    displayedMonth: CalendarMonth,
    prevEnabled: Boolean,
    nextEnabled: Boolean,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = screenPadding),
    ) {
        LeftIcon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .noRippleClickable(enabled = prevEnabled, onClick = onPrev),
            color = Slate900,
        )
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .noRippleClickable(onClick = onClick),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = displayedMonth.toString(),
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        RightIcon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .noRippleClickable(enabled = nextEnabled, onClick = onNext),
            color = Slate900,
        )
    }
}

@Composable
fun CalendarFilterArea(
    modifier: Modifier = Modifier,
    selectedFilter: CalendarFilter,
    onFilterSelected: (CalendarFilter) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarFilter.entries.fastForEach { filter ->
            CalendarFilter(
                filter = filter,
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
            )
        }
    }
}

@Composable
private fun CalendarFilter(
    modifier: Modifier = Modifier,
    filter: CalendarFilter,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(
                color = White,
                shape = MaterialTheme.shapes.large
            )
            .border(
                width = 1.dp,
                color = if (selected) Lime500 else White,
                shape = MaterialTheme.shapes.large,
            )
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp,
            )
            .noRippleClickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        filter.icon()
        Text(
            text = stringResource(filter.displayName),
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) Slate950 else Slate500,
        )
    }
}

@Composable
fun CalendarWeekDays(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarWeekDays.entries.fastForEach {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = it.backgroundColor,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = it.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    color = it.color
                )
            }
        }
    }
}

@Composable
fun CalendarMonthsList(
    modifier: Modifier = Modifier,
    calendarModel: CalendarModel,
    yearRange: IntRange,
    monthsPagerState: PagerState,
) {
    val firstMonth = remember(yearRange) {
        calendarModel.getMonth(year = yearRange.first, month = 1)
    }

    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        state = monthsPagerState,
    ) {
        val month = calendarModel.plusMonths(from = firstMonth, addedMonthsCount = it)
        val isCurrentMonth = calendarModel.today.year == month.year &&
                calendarModel.today.month == month.month
        CalendarMonth(
            month = month,
            isCurrentMonth = isCurrentMonth,
            todayDayOfMonth = calendarModel.today.dayOfMonth,
        )
    }
}

@Composable
private fun CalendarMonth(
    modifier: Modifier = Modifier,
    month: CalendarMonth,
    isCurrentMonth: Boolean,
    todayDayOfMonth: Int,
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
                        val isToday = isCurrentMonth && todayDayOfMonth == dayOfMonth
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