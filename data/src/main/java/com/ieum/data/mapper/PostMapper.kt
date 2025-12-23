package com.ieum.data.mapper

import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.CommentDto
import com.ieum.data.network.model.post.DietDto
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.data.network.model.post.PostCommentRequestBody
import com.ieum.data.network.model.post.PostDailyRequestBody
import com.ieum.data.network.model.post.PostImageDto
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.AmountEaten
import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.Diet
import com.ieum.domain.model.post.Mood
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostCommentRequest
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostUserInfo
import com.ieum.domain.model.post.PostWellnessRequest

fun PostWellnessRequest.asBody(): PostWellnessRequestBody =
    PostWellnessRequestBody(
        diagnosis = null,
        mood = mood.key,
        unusualSymptoms = unusualSymptoms,
        medicationTaken = medicationTaken,
        diet = diet?.toDto(),
        memo = memo,
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

private fun PostImageDto.toDomain(): ImageSource.Remote = ImageSource.Remote(url)


fun PostDailyRequest.asBody(): PostDailyRequestBody =
    PostDailyRequestBody(
        title = title,
        content = content,
        shared = shared,
    )

fun AllPostDto.toDomain(): Post =
    when (type) {
        PostType.WELLNESS.key -> Post.Wellness(
            id = id,
            userInfo = PostUserInfo(userId, userNickname),
            mood = Mood.fromKey(requireNotNull(mood)),
            unusualSymptoms = unusualSymptoms,
            medicationTaken = requireNotNull(medicationTaken),
            diet = requireNotNull(diet).toDomain(),
            memo = memo,
            imageList = images?.map(PostImageDto::toDomain),
            shared = true,
            isLiked = isLiked,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        PostType.DAILY.key -> Post.Daily(
            id = id,
            userInfo = PostUserInfo(userId, userNickname),
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(PostImageDto::toDomain),
            shared = true,
            isLiked = isLiked,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        else -> throw IllegalArgumentException("Unknown post type: $type")
    }

fun MyPostDto.toDomain(): Post =
    when (type) {
        PostType.WELLNESS.key -> Post.Wellness(
            id = id,
            userInfo = null,
            mood = Mood.fromKey(requireNotNull(mood)),
            unusualSymptoms = unusualSymptoms,
            medicationTaken = requireNotNull(medicationTaken),
            diet = requireNotNull(diet).toDomain(),
            memo = memo,
            imageList = images?.map(PostImageDto::toDomain),
            shared = shared,
            isLiked = isLiked,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        PostType.DAILY.key -> Post.Daily(
            id = id,
            userInfo = null,
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(PostImageDto::toDomain),
            shared = shared,
            isLiked = isLiked,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        else -> throw IllegalArgumentException("Unknown post type: $type")
    }

fun OtherPostDto.toDomain(): Post =
    when (type) {
        PostType.WELLNESS.key -> Post.Wellness(
            id = id,
            userInfo = null,
            mood = Mood.fromKey(requireNotNull(mood)),
            unusualSymptoms = unusualSymptoms,
            medicationTaken = requireNotNull(medicationTaken),
            diet = requireNotNull(diet).toDomain(),
            memo = memo,
            imageList = images?.map(PostImageDto::toDomain),
            shared = true,
            isLiked = isLiked,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        PostType.DAILY.key -> Post.Daily(
            id = id,
            userInfo = null,
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(PostImageDto::toDomain),
            shared = true,
            isLiked = isLiked,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
        else -> throw IllegalArgumentException("Unknown post type: $type")
    }

fun CommentDto.toDomain(): Comment =
    Comment(
        id = id,
        nickname = nickname,
        content = content,
        createdAt = createdAt,
    )

fun PostCommentRequest.asBody(): PostCommentRequestBody =
    PostCommentRequestBody(
        content = content,
        parentId = parentId,
    )