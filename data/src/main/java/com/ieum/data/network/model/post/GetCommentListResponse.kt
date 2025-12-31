package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class GetCommentListResponse(
    val comments: List<CommentDto>
)
