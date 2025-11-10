package com.ieum.data.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthRequestBody(
    val accessToken: String,
)
