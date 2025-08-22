package com.ieum.presentation.model.user

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class SexUiModel(@StringRes val description: Int) {
    MALE(description = R.string.description_sex_male),
    FEMALE(description = R.string.description_sex_female),
}