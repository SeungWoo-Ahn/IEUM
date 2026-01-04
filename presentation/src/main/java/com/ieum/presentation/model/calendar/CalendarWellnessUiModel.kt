package com.ieum.presentation.model.calendar

import com.ieum.presentation.model.post.AmountEatenUiModel
import com.ieum.presentation.model.post.MoodUiModel

data class CalendarWellnessUiModel(
    val dayOfMonth: Int,
    val mood: MoodUiModel,
    val unusualSymptomsExist: Boolean,
    val amountEaten: AmountEatenUiModel?,
)
