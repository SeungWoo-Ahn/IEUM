package com.ieum.presentation.model.calendar

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.ieum.design_system.icon.MealIcon
import com.ieum.design_system.icon.MoodHappyIcon
import com.ieum.design_system.icon.ThunderIcon
import com.ieum.design_system.icon.WellnessIcon
import com.ieum.presentation.R

enum class CalendarFilter(
    @StringRes val displayName: Int,
    val icon: @Composable () -> Unit,
) {
    WELLNESS(
        displayName = R.string.wellness_records,
        icon = { WellnessIcon(size = 20) }
    ),
    MOOD(
        displayName = R.string.mood,
        icon = { MoodHappyIcon(size = 20) }
    ),
    UNUSUAL_SYMPTOMS(
        displayName = R.string.unusual_symptoms,
        icon = { ThunderIcon(size = 20) }
    ),
    DIET(
        displayName = R.string.diet,
        icon = { MealIcon(size = 20) }
    )
}