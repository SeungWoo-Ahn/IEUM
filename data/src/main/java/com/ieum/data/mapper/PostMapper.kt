package com.ieum.data.mapper

import com.ieum.data.database.model.PostEntity
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
            diet = diet?.toDomain(),
            memo = memo,
            imageList = images?.map(PostImageDto::toDomain),
            shared = true,
            isLiked = isLiked,
            isMine = false,
            createdAt = createdAt,
        )
        PostType.DAILY.key -> Post.Daily(
            id = id,
            userInfo = PostUserInfo(userId, userNickname),
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(PostImageDto::toDomain),
            shared = true,
            isLiked = isLiked,
            isMine = false,
            createdAt = createdAt,
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
            diet = diet?.toDomain(),
            memo = memo,
            imageList = images?.map(PostImageDto::toDomain),
            shared = shared,
            isLiked = isLiked,
            isMine = true,
            createdAt = createdAt,
        )
        PostType.DAILY.key -> Post.Daily(
            id = id,
            userInfo = null,
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(PostImageDto::toDomain),
            shared = shared,
            isLiked = isLiked,
            isMine = true,
            createdAt = createdAt,
        )
        else -> throw IllegalArgumentException("Unknown post type: $type")
    }

fun AllPostDto.toEntity(myId: Int): PostEntity =
    PostEntity(
        id = id,
        type = type,
        userId = userId,
        userNickname = userNickname,
        diagnosis = diagnosis,
        mood = mood,
        unusualSymptoms = unusualSymptoms,
        medicationTaken = medicationTaken,
        diet = diet,
        memo = memo,
        title = title,
        content = content,
        images = images?.map(PostImageDto::url),
        shared = true,
        isLiked = isLiked,
        isMine = userId == myId,
        createdAt = createdAt,
    )

fun MyPostDto.toEntity(): PostEntity =
    PostEntity(
        id = id,
        type = type,
        userId = null,
        userNickname = null,
        diagnosis = diagnosis,
        mood = mood,
        unusualSymptoms = unusualSymptoms,
        medicationTaken = medicationTaken,
        diet = diet,
        memo = memo,
        title = title,
        content = content,
        images = images?.map(PostImageDto::url),
        shared = shared,
        isLiked = isLiked,
        isMine = true,
        createdAt = createdAt
    )

fun OtherPostDto.toEntity(userId: Int): PostEntity =
    PostEntity(
        id = id,
        type = type,
        userId = userId,
        userNickname = null,
        diagnosis = diagnosis,
        mood = mood,
        unusualSymptoms = unusualSymptoms,
        medicationTaken = medicationTaken,
        diet = diet,
        memo = memo,
        title = title,
        content = content,
        images = images?.map(PostImageDto::url),
        shared = true,
        isLiked = isLiked,
        isMine = false,
        createdAt = createdAt
    )

fun PostEntity.toDomain(): Post =
    when (PostType.fromKey(type)) {
        PostType.WELLNESS -> Post.Wellness(
            id = id,
            userInfo = if (userId != null && userNickname != null) {
                PostUserInfo(userId, userNickname)
            } else null,
            mood = Mood.fromKey(requireNotNull(mood)),
            unusualSymptoms = unusualSymptoms,
            medicationTaken = requireNotNull(medicationTaken),
            diet = diet?.toDomain(),
            memo = memo,
            imageList = images?.map(ImageSource::Remote),
            shared = shared,
            isLiked = isLiked,
            isMine = isMine,
            createdAt = createdAt,
        )
        PostType.DAILY -> Post.Daily(
            id = id,
            userInfo = if (userId != null && userNickname != null) {
                PostUserInfo(userId, userNickname)
            } else null,
            title = requireNotNull(title),
            content = requireNotNull(content),
            imageList = images?.map(ImageSource::Remote),
            shared = shared,
            isLiked = isLiked,
            isMine = isMine,
            createdAt = createdAt,
        )
    }

fun CommentDto.toDomain(): Comment =
    Comment(
        id = id,
        userId = userId,
        nickname = nickname,
        content = content,
        createdAt = createdAt,
        isMine = false
    )

fun PostCommentRequest.asBody(): PostCommentRequestBody =
    PostCommentRequestBody(
        content = content,
        parentId = parentId,
    )