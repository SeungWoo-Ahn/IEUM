package com.ieum.data.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val tokenType: String,
    val user: OAuthUserDto,
)
