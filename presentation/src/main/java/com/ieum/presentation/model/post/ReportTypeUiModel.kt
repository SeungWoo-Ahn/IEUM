package com.ieum.presentation.model.post

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class ReportTypeUiModel(@StringRes val displayName: Int) {
    ABUSE(R.string.description_abuse),
    VIOLENCE(R.string.description_violence),
    SEXUAL(R.string.description_sexual),
    FRAUD(R.string.description_fraud),
    ADVERTISEMENT(R.string.description_advertisement),
    UNTRUTH(R.string.description_untruth),
}