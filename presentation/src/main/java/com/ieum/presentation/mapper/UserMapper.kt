package com.ieum.presentation.mapper

import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.Sex
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiKey
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel
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

fun DiagnoseUiKey.toDomain(): Diagnose =
    when (this) {
        DiagnoseUiKey.LIVER_TRANSPLANT -> Diagnose.LiverTransplant
        DiagnoseUiKey.OTHERS -> Diagnose.Others
    }

private fun CancerStageUiModel.toDomain(): CancerStage? =
    when (this) {
        CancerStageUiModel.STAGE_1 -> CancerStage.STAGE_1
        CancerStageUiModel.STAGE_2 -> CancerStage.STAGE_2
        CancerStageUiModel.STAGE_3 -> CancerStage.STAGE_3
        CancerStageUiModel.STAGE_4 -> CancerStage.STAGE_4
        CancerStageUiModel.STAGE_UNKNOWN -> null
    }

fun CancerDiagnoseUiModel.toDomain(): CancerDiagnose? =
    stage.toDomain()?.let {
        when (key) {
            CancerDiagnoseUiKey.RECTAL_CANCER -> CancerDiagnose.RectalCancer(cancerStage = it)
            CancerDiagnoseUiKey.COLON_CANCER -> CancerDiagnose.ColonCancer(cancerStage = it)
        }
    }
