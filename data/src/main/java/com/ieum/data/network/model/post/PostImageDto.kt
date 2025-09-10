package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

sealed class PostImageDto {
    @Serializable
    data class ForRequest(
        val filename: String,
        val base64Data: String,
    ) : PostImageDto()

    @Serializable
    data class ForResponse(
        val url: String,
    )
}