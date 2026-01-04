package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostCommentRequestBody(
    val content: String,
    val parentId: Int? = null,
)
