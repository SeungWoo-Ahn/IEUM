package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.ieum.design_system.icon.DailyIcon
import com.ieum.design_system.icon.WellnessIcon
import com.ieum.presentation.R

enum class PostTypeUiModel(
    @StringRes val displayName: Int,
    @StringRes val guide: Int,
    val icon: @Composable (Int) -> Unit,
) {
    WELLNESS(
        displayName = R.string.wellness_records,
        guide = R.string.guide_wellness_records,
        icon = { WellnessIcon(size = it) },
    ),
    DAILY(
        displayName = R.string.daily_records,
        guide = R.string.guide_daily_records,
        icon = { DailyIcon(size = it) }
    ),
}