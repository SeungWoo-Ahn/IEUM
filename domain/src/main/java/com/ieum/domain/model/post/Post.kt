package com.ieum.domain.model.post

import com.ieum.domain.model.image.ImageSource

sealed class Post {
    abstract val id: Int
    abstract val userId: Int
    abstract val userNickname: String
    abstract val imageList: List<ImageSource.Remote>?
    abstract val createdAt: Int
    abstract val updatedAt: Int

    data class Wellness(
        override val id: Int,
        override val userId: Int,
        override val userNickname: String,
        val mood: Mood,
        val unusualSymptoms: String?,
        val medicationTaken: Boolean,
        val diet: Diet?,
        val memo: String?,
        override val imageList: List<ImageSource.Remote>?,
        override val createdAt: Int,
        override val updatedAt: Int,
    ) : Post()

    data class Daily(
        override val id: Int,
        override val userId: Int,
        override val userNickname: String,
        val title: String?,
        val content: String,
        override val imageList: List<ImageSource.Remote>?,
        override val createdAt: Int,
        override val updatedAt: Int,
    ) : Post()
}