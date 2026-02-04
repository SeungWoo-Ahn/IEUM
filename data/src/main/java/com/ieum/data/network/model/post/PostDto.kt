package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

interface BasePostDto {
    val id: Int
    val userId: Int
    val userNickname: String
    val type: String
    val images: List<PostImageDto>?
    val isLiked: Boolean
    val likesCount: Int
    val createdAt: Int
    val updatedAt: Int
}

internal interface WellnessDto {
    val diagnosis: List<String>?
    val mood: Int?
    val unusualSymptoms: String?
    val medicationTaken: Boolean?
    val diet: DietDto?
    val memo: String?
}

internal interface DailyDto {
    val title: String?
    val content: String?
}

internal interface SharePostDto {
    val shared: Boolean
}

@Serializable
data class AllPostDto(
    override val id: Int,
    override val userId: Int,
    override val userNickname: String,
    override val type: String,
    override val diagnosis: List<String>? = null,
    override val mood: Int? = null,
    override val unusualSymptoms: String? = null,
    override val medicationTaken: Boolean? = null,
    override val diet: DietDto? = null,
    override val memo: String? = null,
    override val title: String? = null,
    override val content: String? = null,
    override val images: List<PostImageDto>? = null,
    override val isLiked: Boolean,
    override val likesCount: Int,
    override val createdAt: Int,
    override val updatedAt: Int,
) : BasePostDto, WellnessDto, DailyDto

@Serializable
data class MyPostDto(
    override val id: Int,
    override val userId: Int,
    override val userNickname: String,
    override val type: String,
    override val diagnosis: List<String>? = null,
    override val mood: Int? = null,
    override val unusualSymptoms: String? = null,
    override val medicationTaken: Boolean? = null,
    override val diet: DietDto? = null,
    override val memo: String? = null,
    override val title: String? = null,
    override val content: String? = null,
    override val images: List<PostImageDto>? = null,
    override val shared: Boolean,
    override val isLiked: Boolean,
    override val likesCount: Int,
    override val createdAt: Int,
    override val updatedAt: Int,
) : BasePostDto, WellnessDto, DailyDto, SharePostDto

@Serializable
data class OtherPostDto(
    override val id: Int,
    override val userId: Int,
    override val userNickname: String,
    override val type: String,
    override val diagnosis: List<String>? = null,
    override val mood: Int? = null,
    override val unusualSymptoms: String? = null,
    override val medicationTaken: Boolean? = null,
    override val diet: DietDto? = null,
    override val memo: String? = null,
    override val title: String? = null,
    override val content: String? = null,
    override val images: List<PostImageDto>? = null,
    override val isLiked: Boolean,
    override val likesCount: Int,
    override val createdAt: Int,
    override val updatedAt: Int,
) : BasePostDto, WellnessDto, DailyDto