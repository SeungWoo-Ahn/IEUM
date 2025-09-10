package com.ieum.data.network.model.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class PostDto {
    abstract val id: Int
    abstract val userId: Int
    abstract val userNickname: String
    abstract val images: List<PostImageDto.ForResponse>?
    abstract val createdAt: Int
    abstract val updatedAt: Int

    @Serializable
    @SerialName("wellness")
    data class Wellness(
        override val id: Int,
        override val userId: Int,
        override val userNickname: String,
        val diagnosis: String?,
        val mood: Int?,
        val unusualSymptoms: String?,
        val medicationTaken: Boolean?,
        val diet: DietDto?,
        val memo: String?,
        override val images: List<PostImageDto.ForResponse>?,
        override val createdAt: Int,
        override val updatedAt: Int,
    ) : PostDto()

    @Serializable
    @SerialName("daily")
    data class Daily(
        override val id: Int,
        override val userId: Int,
        override val userNickname: String,
        val title: String?,
        val content: String?,
        override val images: List<PostImageDto.ForResponse>?,
        override val createdAt: Int,
        override val updatedAt: Int
    ) : PostDto()
}