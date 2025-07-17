package com.ieum.data.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class OAuthRequestBody(
    val provider: String,
    val authorizationCode: String,
    val redirectUri: String? = null,
)
