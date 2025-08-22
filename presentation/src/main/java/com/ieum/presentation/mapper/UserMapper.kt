package com.ieum.presentation.mapper

import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.R
import com.ieum.presentation.model.user.CancerDiagnoseUiKey
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.DiagnoseUiKey
import com.ieum.presentation.model.user.UserTypeUiModel

fun UserTypeUiModel.toDomain(): UserType =
    when (this) {
        UserTypeUiModel.PATIENT -> UserType.PATIENT
        UserTypeUiModel.CAREGIVER -> UserType.CAREGIVER
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

fun DiagnoseUiKey.toDomain(): Diagnose =
    when (this) {
        DiagnoseUiKey.LIVER_TRANSPLANT -> Diagnose.LiverTransplant
        DiagnoseUiKey.OTHERS -> Diagnose.Others
    }

fun CancerDiagnoseUiModel.toDomain(): CancerDiagnose? =
    stage?.let {
        when (key) {
            CancerDiagnoseUiKey.RENTAL_CANCER -> CancerDiagnose.RectalCancer(it)
            CancerDiagnoseUiKey.COLON_CANCER -> CancerDiagnose.ColonCancer(it)
        }
    }
