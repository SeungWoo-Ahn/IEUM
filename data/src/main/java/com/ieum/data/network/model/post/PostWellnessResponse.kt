package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostWellnessResponse(
    val id: Int,
    val userId: Int,
    val userNickname: String,
    val type: String,
    val diagnosis: List<String>,
    val mood: Int,
    val unusualSymptoms: String? = null,
    val medicationTaken: Boolean,
    val diet: DietDto? = null,
    val memo: String? = null,
    val images: List<PostImageDto>? = null,
    val shared: Boolean,
    val createdAt: Int,
)
