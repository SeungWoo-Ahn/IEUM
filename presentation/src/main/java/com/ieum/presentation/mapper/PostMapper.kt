package com.ieum.presentation.mapper

import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.AmountEaten
import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.Diet
import com.ieum.domain.model.post.Mood
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis
import com.ieum.presentation.model.post.AmountEatenUiModel
import com.ieum.presentation.model.post.CommentUiModel
import com.ieum.presentation.model.post.DiagnoseFilterUiModel
import com.ieum.presentation.model.post.DietUiModel
import com.ieum.presentation.model.post.MoodUiModel
import com.ieum.presentation.model.post.PostTypeUiModel
import com.ieum.presentation.model.post.PostUiModel
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
        mood = requireNotNull(mood).toDomain(),
        unusualSymptoms = unusualSymptoms.ifEmpty { null },
        medicationTaken = medicationTaken ?: false,
        diet = diet?.toDomain(),
        memo = memo.ifEmpty { null },
        imageList = imageList.filterIsInstance<ImageSource.Local>(), // TODO: 이미지 삭제 추가 후 수정
        shared = shared,
    )

fun Post.toUiModel(): PostUiModel =
    when (this) {
        is Post.Wellness -> PostUiModel.Wellness(
            id = id,
            userInfo = userInfo,
            mood = mood.toUiModel(),
            unusualSymptoms = unusualSymptoms,
            medicationTaken = medicationTaken,
            diet = diet?.toUiModel(),
            memo = memo,
            imageList = imageList,
            shared = shared,
            isLiked = isLiked,
            isMine = isMine,
            createdAt = formatDate(DateFormatStrategy.FullDate(createdAt))
        )
        is Post.Daily -> PostUiModel.Daily(
            id = id,
            userInfo = userInfo,
            title = title,
            content = content,
            imageList = imageList,
            shared = shared,
            isLiked = isLiked,
            isMine = isMine,
            createdAt = formatDate(DateFormatStrategy.FullDate(createdAt))
        )
    }

fun DiagnoseFilterUiModel.toDomain(): Diagnosis? =
    when (this) {
        DiagnoseFilterUiModel.ENTIRE -> null
        DiagnoseFilterUiModel.COLON_CANCER -> Diagnosis.COLON_CANCER
        DiagnoseFilterUiModel.RECTAL_CANCER -> Diagnosis.RECTAL_CANCER
        DiagnoseFilterUiModel.LIVER_TRANSPLANT -> Diagnosis.LIVER_TRANSPLANT
        DiagnoseFilterUiModel.OTHERS -> Diagnosis.OTHERS
    }

fun PostTypeUiModel.toDomain(): PostType =
    when (this) {
        PostTypeUiModel.WELLNESS -> PostType.WELLNESS
        PostTypeUiModel.DAILY -> PostType.DAILY
    }

fun Comment.toUiModel(): CommentUiModel =
    CommentUiModel(
        id = id,
        nickname = nickname,
        content = content,
        isMine = isMine
    )