package com.ieum.data.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthRequest(
    val provider: String,
    val authorizationCode: String,
    val redirectUri: String? = null,
)
