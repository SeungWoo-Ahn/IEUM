package com.ieum.domain.model.user

import com.ieum.domain.model.auth.OAuthProvider

data class OthersProfile(
    val id: Int,
    val oAuthProvider: OAuthProvider,
    val userType: UserType,
    val nickname: String,
    val sex: Sex?,
    val diagnoses: List<Diagnose>?,
    val chemotherapy: Chemotherapy?,
    val radiationTherapy: RadiationTherapy?,
    val ageGroup: AgeGroup?,
    val residenceArea: String?,
    val hospitalArea: String?,
)
