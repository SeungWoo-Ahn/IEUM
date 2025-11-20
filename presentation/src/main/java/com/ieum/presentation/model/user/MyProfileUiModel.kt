package com.ieum.presentation.model.user

import com.ieum.domain.model.user.ProfileProperty

data class MyProfileUiModel(
    val id: Int,
    val email: String,
    val nickname: String,
    val diagnoses: ProfileProperty<List<String>>,
    val chemotherapy: ProfileProperty<String>,
    val radiationTherapy: ProfileProperty<String>,
    val ageGroup: ProfileProperty<AgeGroupUiModel>,
    val residenceArea: ProfileProperty<String>,
    val hospitalArea: ProfileProperty<String>,
)
