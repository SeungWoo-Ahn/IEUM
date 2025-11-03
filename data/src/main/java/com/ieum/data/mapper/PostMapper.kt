package com.ieum.data.mapper

import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.DietDto
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.data.network.model.post.PostDailyRequestBody
import com.ieum.data.network.model.post.PostImageDto
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.AmountEaten
import com.ieum.domain.model.post.Diet
import com.ieum.domain.model.post.Mood
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest

suspend fun PostWellnessRequest.asBody(): PostWellnessRequestBody =
    PostWellnessRequestBody(
        diagnosis = diagnosis.key,
        mood = mood.key,
        unusualSymptoms = unusualSymptoms,
        medicationTaken = medicationTaken ?: false,
        diet = (diet ?: Diet.DEFAULT).toDto(),
        memo = memo,
        images = imageList.mapNotNull { it.toDto() }.ifEmpty { null },
        shared = shared,
    )

private fun Diet.toDto(): DietDto =
    DietDto(
        amountEaten = amountEaten.key,
        mealContent = mealContent,
    )

private fun DietDto.toDomain(): Diet =
    Diet(
        amountEaten = AmountEaten.fromKey(amountEaten),
        mealContent = mealContent,
    )

private suspend fun ImageSource.Local.toDto(): PostImageDto.ForRequest? {
    val base64Data = Base64Encoder.encode(file).getOrNull() ?: return null
    return PostImageDto.ForRequest(
        filename = file.name,
        base64Data = base64Data,
    )
}

private fun PostImageDto.ForResponse.toDomain(): ImageSource.Remote =
    ImageSource.Remote(url = url)

suspend fun PostDailyRequest.asBody(): PostDailyRequestBody =
    PostDailyRequestBody(
        title = title,
        content = content,
        images = imageList.mapNotNull { it.toDto() }.ifEmpty { null },
        shared = shared,
    )

fun AllPostDto.toDomain(): Post =
    when (type) {
        PostType.WELLNESS.key -> Post.Wellness(
            id = id,
            userId = userId,
            userNickname = userNickname,
            mood = Mood.fromKey(requireNotNull(mood)),
            unusualSymptoms = unusualSymptoms,
            medicationTaken = requireNotNull(medicationTaken),
            diet = requireNotNull(diet).toDomain(),
            memo = memo,
            imageList = images?.map(PostImageDto.ForResponse::toDomain),
            shared = true,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        PostType.DAILY.key -> Post.Daily(
            id = id,
            userId = userId,
            userNickname = userNickname,
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(PostImageDto.ForResponse::toDomain),
            shared = true,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        else -> throw IllegalArgumentException("Unknown post type: $type")
    }

fun MyPostDto.toDomain(): Post =
    when (type) {
        PostType.WELLNESS.key -> Post.Wellness(
            id = id,
            userId = null,
            userNickname = null,
            mood = Mood.fromKey(requireNotNull(mood)),
            unusualSymptoms = unusualSymptoms,
            medicationTaken = requireNotNull(medicationTaken),
            diet = requireNotNull(diet).toDomain(),
            memo = memo,
            imageList = images?.map(PostImageDto.ForResponse::toDomain),
            shared = shared,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        PostType.DAILY.key -> Post.Daily(
            id = id,
            userId = null,
            userNickname = null,
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(PostImageDto.ForResponse::toDomain),
            shared = shared,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        else -> throw IllegalArgumentException("Unknown post type: $type")
    }

fun OtherPostDto.toDomain(): Post =
    when (type) {
        PostType.WELLNESS.key -> Post.Wellness(
            id = id,
            userId = null,
            userNickname = null,
            mood = Mood.fromKey(requireNotNull(mood)),
            unusualSymptoms = unusualSymptoms,
            medicationTaken = requireNotNull(medicationTaken),
            diet = requireNotNull(diet).toDomain(),
            memo = memo,
            imageList = images?.map(PostImageDto.ForResponse::toDomain),
            shared = true,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        PostType.DAILY.key -> Post.Daily(
            id = id,
            userId = null,
            userNickname = null,
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(PostImageDto.ForResponse::toDomain),
            shared = true,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        else -> throw IllegalArgumentException("Unknown post type: $type")
    }