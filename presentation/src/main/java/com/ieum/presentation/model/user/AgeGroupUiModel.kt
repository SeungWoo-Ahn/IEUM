package com.ieum.presentation.model.user

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class AgeGroupUiModel(@StringRes val description: Int) {
    UNDER_THIRTY(description = R.string.description_under_30),
    FORTIES(description = R.string.description_40s),
    FIFTIES(description = R.string.description_50s),
    SIXTIES(description = R.string.description_60s),
    OVER_SEVENTY(description = R.string.description_over_70);
}