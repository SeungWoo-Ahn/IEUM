package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class GetUserPostListResponse(
    val posts: List<UserPostDto>,
    val pagination: PostPagination,
)
