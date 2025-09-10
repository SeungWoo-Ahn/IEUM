package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class GetPostListResponse(
    val posts: List<PostDto>,
    val pagination: PostPagination,
)
