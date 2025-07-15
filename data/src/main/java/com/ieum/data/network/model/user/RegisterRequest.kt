package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val userType: String,
    val nickname: String,
    val diagnoses: List<DiagnoseDto>,
    val ageGroup: String?,
    val residenceArea: String?,
    val hospitalArea: String?,
)
