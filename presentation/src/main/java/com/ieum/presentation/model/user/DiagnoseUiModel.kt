package com.ieum.presentation.model.user

import androidx.annotation.StringRes
import com.ieum.domain.model.user.CancerStage
import com.ieum.presentation.R

enum class DiagnoseKey(@StringRes val displayName: Int) {
    LIVER_TRANSPLANT(displayName = R.string.liver_transplant),
    OTHERS(displayName = R.string.others),
}

enum class CancerDiagnoseKey(@StringRes val displayName: Int) {
    RENTAL_CANCER(displayName = R.string.rental_cancer),
    COLON_CANCER(displayName = R.string.colon_cancer),
}

data class CancerDiagnoseUiModel(
    val key: CancerDiagnoseKey,
    val stage: CancerStage? = null
)