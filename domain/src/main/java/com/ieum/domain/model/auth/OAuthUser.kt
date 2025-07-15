package com.ieum.domain.model.auth

data class OAuthUser(
    val oauthId: String,
    val provider: OAuthProvider,
    val email: String,
    val name: String?,
    val profileImage: String?,
    val isRegistered: Boolean,
)