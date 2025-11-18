package com.ieum.presentation.model.user

data class OthersProfileUiModel(
    val id: Int,
    val nickname: String,
    val diagnoses: List<String>?,
    val chemotherapy: String?,
    val radiationTherapy: String?,
    val ageGroup: AgeGroupUiModel?,
    val residenceArea: String?,
    val hospitalArea: String?,
) {
    val openedDataEmpty: Boolean get() = diagnoses.isNullOrEmpty() &&
            chemotherapy.isNullOrEmpty() &&
            radiationTherapy.isNullOrEmpty() &&
            ageGroup == null && residenceArea.isNullOrEmpty() &&
            hospitalArea.isNullOrEmpty()
}
