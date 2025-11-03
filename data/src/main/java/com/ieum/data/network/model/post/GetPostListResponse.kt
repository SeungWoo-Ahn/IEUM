package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class GetPostListResponse<T : BasePostDto>(
    val posts: List<T>,
    val pagination: PostPagination,
)
