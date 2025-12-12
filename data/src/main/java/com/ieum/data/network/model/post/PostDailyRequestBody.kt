package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostDailyRequestBody(
    val title: String,
    val content: String,
    val shared: Boolean,
)
