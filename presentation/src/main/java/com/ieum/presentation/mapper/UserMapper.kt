package com.ieum.presentation.mapper

import com.ieum.domain.model.user.UserType
import com.ieum.presentation.R

fun UserType.toDescription(): Int =
    when (this) {
        UserType.PATIENT -> R.string.description_user_type_patient
        UserType.CAREGIVER -> R.string.description_user_type_caregiver
    }