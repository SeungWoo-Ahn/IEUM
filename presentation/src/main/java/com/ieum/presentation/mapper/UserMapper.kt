package com.ieum.presentation.mapper

import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.R

fun UserType.toDescription(): Int =
    when (this) {
        UserType.PATIENT -> R.string.description_user_type_patient
        UserType.CAREGIVER -> R.string.description_user_type_caregiver
    }

fun AgeGroup.toDescription(): Int =
    when (this) {
        AgeGroup.UNDER_THIRTY-> R.string.description_under_30
        AgeGroup.FORTIES -> R.string.description_40s
        AgeGroup.FIFTIES -> R.string.description_50s
        AgeGroup.SIXTIES -> R.string.description_60s
        AgeGroup.OVER_SEVENTY -> R.string.description_over_70
    }