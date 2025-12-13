package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ieum.design_system.icon.DownIcon
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