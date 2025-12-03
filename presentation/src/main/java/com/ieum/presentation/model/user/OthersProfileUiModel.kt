package com.ieum.presentation.model.user

data class OthersProfileUiModel(
    val id: Int,
    val nickname: String,
    val diagnoses: List<String>?,
    val surgery: List<String>?,
    val chemotherapy: List<String>?,
    val radiationTherapy: List<String>?,
    val ageGroup: AgeGroupUiModel?,
    val residenceArea: String?,
    val hospitalArea: String?,
) {
    val openedDataEmpty: Boolean get() = diagnoses.isNullOrEmpty() &&
            surgery.isNullOrEmpty() &&
            chemotherapy.isNullOrEmpty() &&
            radiationTherapy.isNullOrEmpty() &&
            ageGroup == null &&
            residenceArea.isNullOrEmpty() &&
            hospitalArea.isNullOrEmpty()
}
