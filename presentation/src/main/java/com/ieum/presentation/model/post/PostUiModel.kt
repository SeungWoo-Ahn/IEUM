package com.ieum.presentation.model.post

import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.PostUserInfo

sealed class PostUiModel {
    abstract val id: Int
    abstract val userInfo: PostUserInfo?
    abstract val imageList: List<ImageSource.Remote>?
    abstract val shared: Boolean
    abstract val createdAt: String

    data class Wellness(
        override val id: Int,
        override val userInfo: PostUserInfo?,
        val mood: MoodUiModel,
        val unusualSymptoms: String?,
        val medicationTaken: Boolean,
        val diet: DietUiModel?,
        val memo: String?,
        override val imageList: List<ImageSource.Remote>?,
        override val shared: Boolean,
        override val createdAt: String
    ) : PostUiModel()

    data class Daily(
        override val id: Int,
        override val userInfo: PostUserInfo?,
        val title: String,
        val content: String,
        override val imageList: List<ImageSource.Remote>?,
        override val shared: Boolean,
        override val createdAt: String
    ) : PostUiModel()
}