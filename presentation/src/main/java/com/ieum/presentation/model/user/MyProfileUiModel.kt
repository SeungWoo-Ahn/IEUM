package com.ieum.presentation.model.user

import com.ieum.domain.model.user.ProfileProperty

data class MyProfileUiModel(
    val id: Int,
    val email: String,
    val nickname: String,
    val diagnoses: ProfileProperty<List<String>>,
    val surgery: ProfileProperty<List<String>>,
    val chemotherapy: ProfileProperty<List<String>>,
    val radiationTherapy: ProfileProperty<List<String>>,
    val ageGroup: ProfileProperty<AgeGroupUiModel>,
    val residenceArea: ProfileProperty<String>,
    val hospitalArea: ProfileProperty<String>,
)
