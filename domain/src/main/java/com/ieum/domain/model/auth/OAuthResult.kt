package com.ieum.domain.model.auth

data class OAuthResult(
    val token: Token,
    val oAuthUser: OAuthUser,
)
