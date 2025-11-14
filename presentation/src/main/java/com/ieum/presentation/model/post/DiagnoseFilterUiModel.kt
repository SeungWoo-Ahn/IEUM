package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class DiagnoseFilterUiModel(@StringRes val displayName: Int) {
    ENTIRE(displayName = R.string.entire),
    COLON_CANCER(displayName = R.string.colon_cancer),
    RECTAL_CANCER(displayName = R.string.rectal_cancer),
    LIVER_TRANSPLANT(displayName = R.string.liver_transplant),
    OTHERS(displayName = R.string.others),
}