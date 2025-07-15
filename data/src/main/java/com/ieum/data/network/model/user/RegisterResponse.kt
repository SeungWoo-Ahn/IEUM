package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val userId: Int,
    val oauthProvider: String,
    val email: String,
    val userType: String,
    val nickname: String,
    val registeredAt: String
)
