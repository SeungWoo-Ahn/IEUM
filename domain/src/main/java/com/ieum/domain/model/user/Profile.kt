package com.ieum.domain.model.user

import com.ieum.domain.model.auth.OAuthProvider

sealed interface DataVisibility<out T> {
    data class Open<T>(val data: T) : DataVisibility<T>

    data class Hide<T>(val data: T) : DataVisibility<T>

    data object None : DataVisibility<Nothing>
}

data class Profile(
    val id: Int,
    val oAuthProvider: OAuthProvider,
    val email: String?,
    val userType: UserType,
    val nickname: String,
    val sex: DataVisibility<Sex>,
    val diagnoses: DataVisibility<List<Diagnose>>,
    val chemotherapy: DataVisibility<Chemotherapy>,
    val radiationTherapy: DataVisibility<RadiationTherapy>,
    val ageGroup: DataVisibility<AgeGroup>,
    val residenceArea: DataVisibility<String>,
    val hospitalArea: DataVisibility<String>,
)