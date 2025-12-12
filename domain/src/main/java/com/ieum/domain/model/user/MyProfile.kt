package com.ieum.domain.model.user

import com.ieum.domain.model.auth.OAuthProvider

data class ProfileProperty<T>(
    val data: T?,
    val open: Boolean,
)

data class MyProfile(
    val id: Int,
    val oAuthProvider: OAuthProvider,
    val email: String,
    val userType: UserType,
    val nickname: String,
    val sex: ProfileProperty<Sex>,
    val diagnoses: ProfileProperty<List<Diagnose>>,
    val surgery: ProfileProperty<List<Surgery>>,
    val chemotherapy: ProfileProperty<List<Chemotherapy>>,
    val radiationTherapy: ProfileProperty<List<RadiationTherapy>>,
    val ageGroup: ProfileProperty<AgeGroup>,
    val residenceArea: ProfileProperty<String>,
    val hospitalArea: ProfileProperty<String>,
)
