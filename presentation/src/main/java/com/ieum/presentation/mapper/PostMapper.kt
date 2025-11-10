package com.ieum.presentation.mapper

import com.ieum.domain.model.post.AmountEaten
import com.ieum.domain.model.post.Diet
import com.ieum.domain.model.post.Mood
import com.ieum.domain.model.post.Post
import com.ieum.presentation.model.post.AmountEatenUiModel
import com.ieum.presentation.model.post.DietUiModel
import com.ieum.presentation.model.post.MoodUiModel
import com.ieum.presentation.model.post.PostWellnessUiModel

private fun Mood.toUiModel(): MoodUiModel =
    when (this) {
        Mood.HAPPY -> MoodUiModel.HAPPY
        Mood.GOOD -> MoodUiModel.GOOD
        Mood.NORMAL -> MoodUiModel.NORMAL
        Mood.BAD -> MoodUiModel.BAD
        Mood.WORST -> MoodUiModel.WORST
    }

private fun AmountEaten.toUiModel(): AmountEatenUiModel =
    when (this) {
        AmountEaten.WELL -> AmountEatenUiModel.WELL
        AmountEaten.SMALL -> AmountEatenUiModel.SMALL
        AmountEaten.BARELY -> AmountEatenUiModel.BARELY
    }

private fun Diet.toUiModel(): DietUiModel =
    DietUiModel(
        amountEaten = amountEaten.toUiModel(),
        mealContent = mealContent ?: ""
    )

fun Post.Wellness.toUiModel(): PostWellnessUiModel =
    PostWellnessUiModel(
        mood = mood.toUiModel(),
        unusualSymptoms = unusualSymptoms ?: "",
        medicationTaken = medicationTaken,
        diet = diet?.toUiModel(),
        memo = memo ?: "",
        imageList = emptyList(), // TODO: 이미지 삭제 추가 후 수정
        shared = shared,
    )