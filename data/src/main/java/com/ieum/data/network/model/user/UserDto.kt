package com.ieum.data.network.model.user

import com.ieum.domain.model.user.Diagnose
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val userId: String,
    val oauthProvider: String,
    val email: String,
    val userType: String,
    val nickname: String,
    val diagnoses: List<Diagnose>,
    val ageGroup: String?,
    val residenceArea: String?,
    val hospitalArea: String?,
)
