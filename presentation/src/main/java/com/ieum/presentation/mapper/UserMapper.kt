package com.ieum.presentation.mapper

import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Chemotherapy
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.model.user.OthersProfile
import com.ieum.domain.model.user.RadiationTherapy
import com.ieum.domain.model.user.Sex
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiKey
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel
import com.ieum.presentation.model.user.DiagnoseUiKey
import com.ieum.presentation.model.user.DiagnoseUiKeys
import com.ieum.presentation.model.user.OthersProfileUiModel
import com.ieum.presentation.model.user.SexUiModel
import com.ieum.presentation.model.user.UserTypeUiModel
import com.ieum.presentation.util.GlobalValueModel

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

private fun AgeGroup.toUiModel(): AgeGroupUiModel =
    when (this) {
        AgeGroup.UNDER_THIRTY -> AgeGroupUiModel.UNDER_THIRTY
        AgeGroup.FORTIES -> AgeGroupUiModel.FORTIES
        AgeGroup.FIFTIES -> AgeGroupUiModel.FIFTIES
        AgeGroup.SIXTIES -> AgeGroupUiModel.SIXTIES
        AgeGroup.OVER_SEVENTY -> AgeGroupUiModel.OVER_SEVENTY
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

private fun CancerStage.toUiModel(): CancerStageUiModel =
    when (this) {
        CancerStage.STAGE_1 -> CancerStageUiModel.STAGE_1
        CancerStage.STAGE_2 -> CancerStageUiModel.STAGE_2
        CancerStage.STAGE_3 -> CancerStageUiModel.STAGE_3
        CancerStage.STAGE_4 -> CancerStageUiModel.STAGE_4
    }

fun CancerDiagnoseUiModel.toDomain(): CancerDiagnose? =
    stage.toDomain()?.let {
        when (key) {
            CancerDiagnoseUiKey.RECTAL_CANCER -> CancerDiagnose.RectalCancer(cancerStage = it)
            CancerDiagnoseUiKey.COLON_CANCER -> CancerDiagnose.ColonCancer(cancerStage = it)
        }
    }

private fun Diagnosis.toUiKey(): DiagnoseUiKeys =
    when (this) {
        Diagnosis.COLON_CANCER -> CancerDiagnoseUiKey.COLON_CANCER
        Diagnosis.RECTAL_CANCER -> CancerDiagnoseUiKey.RECTAL_CANCER
        Diagnosis.LIVER_TRANSPLANT -> DiagnoseUiKey.LIVER_TRANSPLANT
        Diagnosis.OTHERS -> DiagnoseUiKey.OTHERS
    }

private fun Diagnose.toUiModel(valueModel: GlobalValueModel): String =
    when (this) {
        is CancerDiagnose -> {
            val diagnose = name.toUiKey()
            val cancerStage = cancerStage.toUiModel()
            "${valueModel.getString(diagnose.displayName)}: ${valueModel.getString(cancerStage.description)}"
        }
        else -> {
            val diagnose = name.toUiKey()
            valueModel.getString(diagnose.displayName)
        }
    }

private fun Chemotherapy.toUiModel(): String = "$startDate (${cycle}차수)"

private fun RadiationTherapy.toUiModel(): String = if (endDate == null) {
    "$startDate (진행중)"
} else {
    "$startDate~$endDate"
}

fun OthersProfile.toUiModel(valueModel: GlobalValueModel): OthersProfileUiModel =
    OthersProfileUiModel(
        id = id,
        nickname = nickname,
        diagnoses = diagnoses?.map { it.toUiModel(valueModel) },
        chemotherapy = chemotherapy?.let(Chemotherapy::toUiModel),
        radiationTherapy = radiationTherapy?.let(RadiationTherapy::toUiModel),
        ageGroup = ageGroup?.toUiModel(),
        residenceArea = residenceArea,
        hospitalArea = hospitalArea,
    )
