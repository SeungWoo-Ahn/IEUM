package com.ieum.domain.model.post

data class Comment(
    val id: Int,
    val userId: Int,
    val nickname: String,
    val content: String,
    val createdAt: Int,
    val isMine: Boolean,
)
