package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: Int,
    val userId: Int,
    val nickname: String,
    val content: String,
    val createdAt: Int,
    val updatedAt: Int,
)
