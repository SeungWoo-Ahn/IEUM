package com.ieum.presentation.model.calendar

import androidx.compose.ui.graphics.Color
import com.ieum.design_system.theme.Red100
import com.ieum.design_system.theme.Red500
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate900

enum class CalendarWeekDays(
    val displayName: String,
    val color: Color = Slate900,
    val backgroundColor: Color = Slate200,
) {
    SUNDAY(
        displayName = "일",
        color = Red500,
        backgroundColor = Red100,
    ),
    MONDAY(displayName = "월"),
    TUESDAY(displayName = "화"),
    WEDNESDAY(displayName = "수"),
    THURSDAY(displayName = "목"),
    FRIDAY(displayName = "금"),
    SATURDAY(displayName = "토"),
}