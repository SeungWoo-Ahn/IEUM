package com.ieum.presentation.mapper

import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.R
import com.ieum.presentation.model.user.CancerDiagnoseKey
import com.ieum.presentation.model.user.DiagnoseKey

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

fun CancerStage?.toDescription(): Int =
    when (this) {
        CancerStage.STAGE_1 -> R.string.cancer_stage_1
        CancerStage.STAGE_2 -> R.string.cancer_stage_2
        CancerStage.STAGE_3 -> R.string.cancer_stage_3
        CancerStage.STAGE_4 -> R.string.cancer_stage_4
        else -> R.string.cancer_stage_unknown
    }

fun DiagnoseKey.toDomain(): Diagnose =
    when (this) {
        DiagnoseKey.LIVER_TRANSPLANT -> Diagnose.LiverTransplant
        DiagnoseKey.OTHERS -> Diagnose.Others
    }

fun CancerDiagnoseKey.toDomain(stage: CancerStage): CancerDiagnose =
    when (this) {
        CancerDiagnoseKey.RENTAL_CANCER -> CancerDiagnose.RentalCancer(stage)
        CancerDiagnoseKey.COLON_CANCER -> CancerDiagnose.ColonCancer(stage)
    }