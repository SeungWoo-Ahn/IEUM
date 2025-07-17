package com.ieum.data.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val expiresIn: Int,
    val tokenType: String,
)
