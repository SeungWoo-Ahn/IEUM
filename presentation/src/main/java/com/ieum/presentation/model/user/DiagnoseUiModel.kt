package com.ieum.presentation.model.user

import androidx.annotation.StringRes
import com.ieum.presentation.R

sealed interface DiagnoseUiKeys {
    val displayName: Int
}

enum class DiagnoseUiKey(@StringRes override val displayName: Int) : DiagnoseUiKeys {
    LIVER_TRANSPLANT(displayName = R.string.liver_transplant),
    OTHERS(displayName = R.string.others),
}

enum class CancerDiagnoseUiKey(@StringRes override val displayName: Int) : DiagnoseUiKeys {
    RECTAL_CANCER(displayName = R.string.rectal_cancer),
    COLON_CANCER(displayName = R.string.colon_cancer),
}

data class CancerDiagnoseUiModel(
    val key: CancerDiagnoseUiKey,
    val stage: CancerStageUiModel,
) {
    val isSelected: Boolean get() = stage != CancerStageUiModel.STAGE_UNKNOWN
}