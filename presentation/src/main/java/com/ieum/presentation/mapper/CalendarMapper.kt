package com.ieum.presentation.mapper

import com.ieum.domain.model.post.Post
import com.ieum.presentation.model.calendar.CalendarWellnessUiModel
import com.ieum.presentation.model.post.AmountEatenUiModel
import com.ieum.presentation.model.post.MoodUiModel
import kotlin.math.roundToInt

fun List<MoodUiModel>.calcAverage(): MoodUiModel {
    if (isEmpty()) return MoodUiModel.NORMAL
    val averageScore = map { it.ordinal }.average()
    val nearestIndex = averageScore.roundToInt().coerceIn(0, MoodUiModel.entries.lastIndex)
    return MoodUiModel.entries[nearestIndex]
}

fun List<AmountEatenUiModel>.calcAverage(): AmountEatenUiModel? {
    if (isEmpty()) return null
    val lastIndex = AmountEatenUiModel.entries.lastIndex
    val averageScore = map { lastIndex - it.ordinal }.average()
    val nearestIndex = lastIndex - averageScore.roundToInt().coerceIn(0, lastIndex)
    return AmountEatenUiModel.entries[nearestIndex]
}

fun Post.Wellness.toCalendarUiModel(): CalendarWellnessUiModel =
    CalendarWellnessUiModel(
        dayOfMonth = 1,
        mood = mood.toUiModel(),
        unusualSymptomsExist = unusualSymptoms != null,
        amountEaten = diet?.amountEaten?.toUiModel()
    )