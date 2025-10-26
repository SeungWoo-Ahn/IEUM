package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

internal interface BasePostDto {
    val id: Int
    val type: String
    val images: List<PostImageDto.ForResponse>?
    val createdAt: Int
    val updatedAt: Int
}

internal interface WellnessDto {
    val diagnosis: String?
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

@Serializable
data class AllPostDto(
    override val id: Int,
    val userId: Int,
    val userNickname: String,
    override val type: String,
    override val diagnosis: String? = null,
    override val mood: Int? = null,
    override val unusualSymptoms: String? = null,
    override val medicationTaken: Boolean? = null,
    override val diet: DietDto? = null,
    override val memo: String? = null,
    override val title: String? = null,
    override val content: String? = null,
    override val images: List<PostImageDto.ForResponse>? = null,
    override val createdAt: Int,
    override val updatedAt: Int,
) : BasePostDto, WellnessDto, DailyDto

@Serializable
data class MyPostDto(
    override val id: Int,
    override val type: String,
    override val diagnosis: String? = null,
    override val mood: Int? = null,
    override val unusualSymptoms: String? = null,
    override val medicationTaken: Boolean? = null,
    override val diet: DietDto? = null,
    override val memo: String? = null,
    override val title: String? = null,
    override val content: String? = null,
    override val images: List<PostImageDto.ForResponse>? = null,
    val shared: Boolean,
    override val createdAt: Int,
    override val updatedAt: Int,
) : BasePostDto, WellnessDto, DailyDto

@Serializable
data class OtherPostDto(
    override val id: Int,
    override val type: String,
    override val diagnosis: String? = null,
    override val mood: Int? = null,
    override val unusualSymptoms: String? = null,
    override val medicationTaken: Boolean? = null,
    override val diet: DietDto? = null,
    override val memo: String? = null,
    override val title: String? = null,
    override val content: String? = null,
    override val images: List<PostImageDto.ForResponse>? = null,
    override val createdAt: Int,
    override val updatedAt: Int,
) : BasePostDto, WellnessDto, DailyDto