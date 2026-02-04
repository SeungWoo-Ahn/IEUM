package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class MonthlyWellnessDto(
    override val id: Int,
    override val userId: Int,
    override val userNickname: String,
    override val type: String,
    override val images: List<PostImageDto>? = null,
    override val mood: Int,
    override val unusualSymptoms: String? = null,
    override val medicationTaken: Boolean,
    override val diet: DietDto? = null,
    override val memo: String? = null,
    override val isLiked: Boolean,
    override val likesCount: Int,
    override val createdAt: Int,
    override val updatedAt: Int,
    override val diagnosis: List<String>,
) : BasePostDto, WellnessDto
