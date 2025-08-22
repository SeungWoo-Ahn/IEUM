package com.ieum.presentation.mapper

import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.Sex
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.R
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiKey
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.DiagnoseUiKey
import com.ieum.presentation.model.user.SexUiModel
import com.ieum.presentation.model.user.UserTypeUiModel

fun UserTypeUiModel.toDomain(): UserType =
    when (this) {
        UserTypeUiModel.PATIENT -> UserType.PATIENT
        UserTypeUiModel.CAREGIVER -> UserType.CAREGIVER
    }

fun SexUiModel.toDomain(): Sex =
    when (this) {
        SexUiModel.MALE -> Sex.MALE
        SexUiModel.FEMALE -> Sex.FEMALE
    }

fun AgeGroupUiModel.toDomain(): AgeGroup =
    when (this) {
        AgeGroupUiModel.UNDER_THIRTY -> AgeGroup.UNDER_THIRTY
        AgeGroupUiModel.FORTIES -> AgeGroup.FORTIES
        AgeGroupUiModel.FIFTIES -> AgeGroup.FIFTIES
        AgeGroupUiModel.SIXTIES -> AgeGroup.SIXTIES
        AgeGroupUiModel.OVER_SEVENTY -> AgeGroup.OVER_SEVENTY
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
