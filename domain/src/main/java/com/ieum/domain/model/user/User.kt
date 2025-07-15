package com.ieum.domain.model.user

import com.ieum.domain.model.auth.OAuthProvider

data class User(
    val id: String,
    val oAuthProvider: OAuthProvider,
    val email: String,
    val userType: UserType,
    val nickName: String,
    val diagnoses: List<Diagnose>,
    val ageGroup: AgeGroup?,
    val residenceArea: String?,
    val hospitalArea: String?,
)
