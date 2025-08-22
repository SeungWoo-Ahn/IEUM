package com.ieum.presentation.model.user

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class UserTypeUiModel(@StringRes val description: Int) {
    PATIENT(description = R.string.description_user_type_patient),
    CAREGIVER(description = R.string.description_user_type_caregiver),
}