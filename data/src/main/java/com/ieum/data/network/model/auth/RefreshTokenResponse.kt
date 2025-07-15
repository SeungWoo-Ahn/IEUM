package com.ieum.data.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val refreshToken: String,
)
