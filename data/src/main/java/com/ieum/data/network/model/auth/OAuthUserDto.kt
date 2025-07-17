package com.ieum.data.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthUserDto(
    val oauthId: String,
    val provider: String,
    val email: String,
    val name: String?,
    val profileImage: String?,
    val isRegistered: Boolean,
)
