package com.ieum.domain.model.auth

data class OAuthRequest(
    val provider: OAuthProvider,
    val code: String,
)
