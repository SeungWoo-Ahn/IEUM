package com.ieum.domain.model.user

data class RegisterRequest(
    val userType: UserType,
    val nickName: String,
    val diagnoses: List<Diagnose>,
    val ageGroup: AgeGroup?,
    val residenceArea: String,
    val hospitalArea: String,
)
