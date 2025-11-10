package com.ieum.presentation.mapper

import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.AmountEaten
import com.ieum.domain.model.post.Diet
import com.ieum.domain.model.post.Mood
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis
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

private fun MoodUiModel.toDomain(): Mood =
    when (this) {
        MoodUiModel.WORST -> Mood.WORST
        MoodUiModel.BAD -> Mood.BAD
        MoodUiModel.NORMAL -> Mood.NORMAL
        MoodUiModel.GOOD -> Mood.GOOD
        MoodUiModel.HAPPY -> Mood.HAPPY
    }

private fun AmountEaten.toUiModel(): AmountEatenUiModel =
    when (this) {
        AmountEaten.WELL -> AmountEatenUiModel.WELL
        AmountEaten.SMALL -> AmountEatenUiModel.SMALL
        AmountEaten.BARELY -> AmountEatenUiModel.BARELY
    }

private fun AmountEatenUiModel.toDomain(): AmountEaten =
    when (this) {
        AmountEatenUiModel.WELL -> AmountEaten.WELL
        AmountEatenUiModel.SMALL -> AmountEaten.SMALL
        AmountEatenUiModel.BARELY -> AmountEaten.BARELY
    }

private fun Diet.toUiModel(): DietUiModel =
    DietUiModel(
        amountEaten = amountEaten.toUiModel(),
        mealContent = mealContent ?: ""
    )

private fun DietUiModel.toDomain(): Diet =
    Diet(
        amountEaten = amountEaten.toDomain(),
        mealContent = mealContent.ifEmpty { null }
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

fun PostWellnessUiModel.toRequest(): PostWellnessRequest =
    PostWellnessRequest(
        diagnosis = Diagnosis.OTHERS, // TODO: 진단 추가 후 수정
        mood = requireNotNull(mood).toDomain(),
        unusualSymptoms = unusualSymptoms.ifEmpty { null },
        medicationTaken = medicationTaken ?: false,
        diet = diet?.toDomain(),
        memo = memo.ifEmpty { null },
        imageList = imageList.filterIsInstance<ImageSource.Local>().ifEmpty { null }, // TODO: 이미지 삭제 추가 후 수정
        shared = shared,
    )