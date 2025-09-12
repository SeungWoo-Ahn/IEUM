package com.ieum.data.network.model.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class UserPostDto {
    abstract val id: Int
    abstract val images: List<PostImageDto.ForResponse>?
    abstract val shared: Boolean
    abstract val createdAt: Int
    abstract val updatedAt: Int

    @Serializable
    @SerialName("wellness")
    data class Wellness(
        override val id: Int,
        val diagnosis: String,
        val mood: Int,
        val unusualSymptoms: String?,
        val medicationTaken: Boolean?,
        val diet: DietDto?,
        val memo: String?,
        override val images: List<PostImageDto.ForResponse>?,
        override val shared: Boolean = false,
        override val createdAt: Int,
        override val updatedAt: Int,
    ) : UserPostDto()

    @Serializable
    @SerialName("daily")
    data class Daily(
        override val id: Int,
        val title: String,
        val content: String,
        override val images: List<PostImageDto.ForResponse>?,
        override val shared: Boolean = false,
        override val createdAt: Int,
        override val updatedAt: Int
    ) : UserPostDto()

}