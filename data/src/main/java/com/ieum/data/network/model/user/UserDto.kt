package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val oauthProvider: String,
    val email: String,
    val userType: String,
    val sex: String,
    val nickname: String,
    val diagnoses: List<DiagnoseDto>,
    val ageGroup: String?,
    val residenceArea: String?,
    val hospitalArea: String?,
)
