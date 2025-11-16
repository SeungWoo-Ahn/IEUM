package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ieum.design_system.icon.MoodBadIcon
import com.ieum.design_system.icon.MoodGoodIcon
import com.ieum.design_system.icon.MoodHappyIcon
import com.ieum.design_system.icon.MoodNormalIcon
import com.ieum.design_system.icon.MoodWorstIcon
import com.ieum.presentation.R

enum class MoodUiModel(
    @StringRes val description: Int,
    val strokeColor: Color,
    val backGroundColor: Color,
    val icon: @Composable () -> Unit,
) {
    WORST(
        description = R.string.mood_worst,
        strokeColor = Color(0x8051A2FF),
        backGroundColor = Color(0x3351A2FF),
        icon = { MoodWorstIcon() }
    ),
    BAD(
        description = R.string.mood_bad,
        strokeColor = Color(0x8000D3F3),
        backGroundColor = Color(0x3300D3F3),
        icon = { MoodBadIcon() }
    ),
    NORMAL(
        description = R.string.mood_normal,
        strokeColor = Color(0x8000BBA7),
        backGroundColor = Color(0x3300D5BE),
        icon = { MoodNormalIcon() }
    ),
    GOOD(
        description = R.string.mood_good,
        strokeColor = Color(0x809AE600),
        backGroundColor = Color(0x339AE600),
        icon = { MoodGoodIcon() }
    ),
    HAPPY(
        description = R.string.mood_happy,
        strokeColor = Color(0x80F0B100),
        backGroundColor = Color(0x33FDC700),
        icon = { MoodHappyIcon() }
    ),
}