package com.ieum.presentation.model.user

sealed interface DiagnoseUiKeys {
    val displayName: String
}

enum class DiagnoseUiKey(override val displayName: String) : DiagnoseUiKeys {
    LIVER_TRANSPLANT(displayName = "간이식"),
    OTHERS(displayName = "기타"),
}

enum class CancerDiagnoseUiKey(override val displayName: String) : DiagnoseUiKeys {
    COLON_CANCER(displayName = "직장암"),
    RECTAL_CANCER(displayName = "대장암"),
}

data class CancerDiagnoseUiModel(
    val key: CancerDiagnoseUiKey,
    val stage: CancerStageUiModel,
) {
    val isSelected: Boolean get() = stage != CancerStageUiModel.STAGE_UNKNOWN
}