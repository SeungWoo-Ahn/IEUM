package com.ieum.domain.model.user

import com.ieum.domain.model.auth.OAuthProvider

sealed interface DataStatus<out T> {
    data class Open<T>(val data: T) : DataStatus<T>

    data class Hide<T>(val data: T) : DataStatus<T>

    data object None : DataStatus<Nothing>
}

data class Profile(
    val id: Int,
    val oAuthProvider: OAuthProvider,
    val email: String?,
    val userType: UserType,
    val nickname: String,
    val sex: DataStatus<Sex>,
    val diagnoses: DataStatus<List<Diagnose>>,
    val chemotherapy: DataStatus<Chemotherapy>,
    val radiationTherapy: DataStatus<RadiationTherapy>,
    val ageGroup: DataStatus<AgeGroup>,
    val residenceArea: DataStatus<String>,
    val hospitalArea: DataStatus<String>,
)