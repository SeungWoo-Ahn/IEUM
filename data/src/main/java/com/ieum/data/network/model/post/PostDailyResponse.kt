package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostDailyResponse(
    val id: Int,
    val userId: Int,
    val userNickname: String,
    val type: String,
    val title: String? = null,
    val content: String,
    val images: List<PostImageDto>? = null,
    val shared: Boolean,
    val createdAt: Int,
)
