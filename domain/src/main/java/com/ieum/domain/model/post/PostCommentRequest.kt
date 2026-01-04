package com.ieum.domain.model.post

data class PostCommentRequest(
    val postId: Int,
    val postType: PostType,
    val content: String,
    val parentId: Int? = null
)
