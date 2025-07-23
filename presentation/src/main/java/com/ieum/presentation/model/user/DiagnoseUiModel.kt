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
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is CancerDiagnoseUiModel) return false
        return key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}