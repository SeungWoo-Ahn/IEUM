package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.ieum.design_system.icon.DownIcon
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.model.calendar.CalendarMonth

@Composable
fun CalendarTopBar(
    modifier: Modifier = Modifier,
    calendarMonth: CalendarMonth,
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
                text = calendarMonth.toString(),
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
        horizontalArrangement = Arrangement.SpaceEvenly,
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