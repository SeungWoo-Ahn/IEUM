package com.ieum.data.mapper

import com.ieum.data.network.model.auth.OAuthProviderDto
import com.ieum.data.network.model.user.AgeGroupDto
import com.ieum.data.network.model.user.CancerStageDto
import com.ieum.data.network.model.user.DiagnoseDto
import com.ieum.data.network.model.user.DiagnoseDtoKey
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.UserDto
import com.ieum.data.network.model.user.UserTypeDto
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.DiagnoseKey
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.User
import com.ieum.domain.model.user.UserType

internal fun UserType.toDto(): UserTypeDto =
    when (this) {
        UserType.PATIENT -> UserTypeDto.PATIENT
        UserType.CAREGIVER -> UserTypeDto.CAREGIVER
    }

internal fun UserTypeDto.toDomain(): UserType =
    when (this) {
        UserTypeDto.PATIENT -> UserType.PATIENT
        UserTypeDto.CAREGIVER -> UserType.CAREGIVER
    }

internal fun AgeGroup.toDto(): AgeGroupDto =
    when (this) {
        AgeGroup.UNDER_THIRTY -> AgeGroupDto.UNDER_THIRTY
        AgeGroup.FORTIES -> AgeGroupDto.FORTIES
        AgeGroup.FIFTIES -> AgeGroupDto.FIFTIES
        AgeGroup.SIXTIES -> AgeGroupDto.SIXTIES
        AgeGroup.OVER_SEVENTY -> AgeGroupDto.OVER_SEVENTY
    }

internal fun AgeGroupDto.toDomain(): AgeGroup =
    when (this) {
        AgeGroupDto.UNDER_THIRTY -> AgeGroup.UNDER_THIRTY
        AgeGroupDto.FORTIES -> AgeGroup.FORTIES
        AgeGroupDto.FIFTIES -> AgeGroup.FIFTIES
        AgeGroupDto.SIXTIES -> AgeGroup.SIXTIES
        AgeGroupDto.OVER_SEVENTY -> AgeGroup.OVER_SEVENTY
    }

internal fun CancerStage.toDto(): CancerStageDto =
    when (this) {
        CancerStage.STAGE_1 -> CancerStageDto.STAGE_1
        CancerStage.STAGE_2 -> CancerStageDto.STAGE_2
        CancerStage.STAGE_3 -> CancerStageDto.STAGE_3
        CancerStage.STAGE_4 -> CancerStageDto.STAGE_4
    }

internal fun CancerStageDto.toDomain(): CancerStage =
    when (this) {
        CancerStageDto.STAGE_1 -> CancerStage.STAGE_1
        CancerStageDto.STAGE_2 -> CancerStage.STAGE_2
        CancerStageDto.STAGE_3 -> CancerStage.STAGE_3
        CancerStageDto.STAGE_4 -> CancerStage.STAGE_4
    }

internal fun DiagnoseKey.toDto(): DiagnoseDtoKey =
    when (this) {
        DiagnoseKey.RENTAL_CANCER -> DiagnoseDtoKey.RENTAL_CANCER
        DiagnoseKey.COLON_CANCER -> DiagnoseDtoKey.COLON_CANCER
        DiagnoseKey.LIVER_TRANSPLANT -> DiagnoseDtoKey.LIVER_TRANSPLANT
        DiagnoseKey.OTHERS -> DiagnoseDtoKey.OTHERS
    }

internal fun Diagnose.toDto(): DiagnoseDto =
    when (this) {
        is CancerDiagnose -> DiagnoseDto(
            name = key.toDto().key,
            cancerStage = cancerStage.toDto().key,
        )
        else -> DiagnoseDto(
            name = key.toDto().key,
            cancerStage = null,
        )
    }

internal fun DiagnoseDto.toDomain(): Diagnose =
    when (DiagnoseDtoKey.fromKey(name)) {
        DiagnoseDtoKey.RENTAL_CANCER -> CancerDiagnose.RentalCancer(
            cancerStage = cancerStage?.let { CancerStageDto.fromKey(it).toDomain() }
                ?: throw IllegalArgumentException("RentalCancer cancerStage is null"),
        )
        DiagnoseDtoKey.COLON_CANCER -> CancerDiagnose.ColonCancer(
            cancerStage = cancerStage?.let { CancerStageDto.fromKey(it).toDomain() }
                ?: throw IllegalArgumentException("ColonCancer cancerStage is null"),
        )
        DiagnoseDtoKey.LIVER_TRANSPLANT -> Diagnose.LiverTransplant
        DiagnoseDtoKey.OTHERS -> Diagnose.Others
    }

fun RegisterRequest.asBody(): RegisterRequestBody =
    RegisterRequestBody(
        userType = userType.toDto().key,
        nickname = nickName,
        diagnoses = diagnoses.map(Diagnose::toDto),
        ageGroup = ageGroup?.toDto()?.key,
        residenceArea = residenceArea,
        hospitalArea = hospitalArea,
    )

fun UserDto.toDomain(): User =
    User(
        id = userId,
        oAuthProvider = OAuthProviderDto.fromKey(oauthProvider).toDomain(),
        email = email,
        userType = UserTypeDto.fromKey(userType).toDomain(),
        nickName = nickname,
        diagnoses = diagnoses.map(DiagnoseDto::toDomain),
        ageGroup = ageGroup?.let { AgeGroupDto.fromKey(it).toDomain() },
        residenceArea = residenceArea,
        hospitalArea = hospitalArea,
    )

