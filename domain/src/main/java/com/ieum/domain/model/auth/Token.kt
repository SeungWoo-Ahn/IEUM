package com.ieum.domain.model.auth

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Int,
)