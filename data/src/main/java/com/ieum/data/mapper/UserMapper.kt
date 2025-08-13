package com.ieum.data.mapper

import com.ieum.data.network.model.user.DiagnoseDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.UserDto
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.User
import com.ieum.domain.model.user.UserType

private fun Diagnose.toDto(): DiagnoseDto =
    when {
        this is CancerDiagnose -> DiagnoseDto(
            diagnosis = name.key,
            cancerStage = cancerStage.key,
        )
        else -> DiagnoseDto(
            diagnosis = name.key,
            cancerStage = null,
        )
    }

private fun DiagnoseDto.toDomain(): Diagnose =
    when (Diagnosis.fromKey(diagnosis)) {
        Diagnosis.RECTAL_CANCER -> cancerStage?.let {
            CancerDiagnose.RectalCancer(CancerStage.fromKey(it))
        } ?: throw IllegalArgumentException("cancerStage needed")
        Diagnosis.COLON_CANCER -> cancerStage?.let {
            CancerDiagnose.ColonCancer(CancerStage.fromKey(it))
        } ?: throw IllegalArgumentException("cancerStage needed")
        Diagnosis.LIVER_TRANSPLANT -> Diagnose.LiverTransplant
        Diagnosis.OTHERS -> Diagnose.Others
    }

fun RegisterRequest.asBody(): RegisterRequestBody =
    RegisterRequestBody(
        userType = userType.key,
        sex = sex.key,
        nickname = nickName,
        diagnoses = diagnoses.map(Diagnose::toDto),
        ageGroup = ageGroup?.key,
        residenceArea = residenceArea,
        hospitalArea = hospitalArea,
    )

fun UserDto.toDomain(): User =
    User(
        id = id,
        oAuthProvider = OAuthProvider.fromKey(oauthProvider),
        email = email,
        userType = UserType.fromKey(userType),
        nickName = nickname,
        diagnoses = diagnoses.map(DiagnoseDto::toDomain),
        ageGroup = ageGroup?.let(AgeGroup::fromKey),
        residenceArea = residenceArea,
        hospitalArea = hospitalArea,
    )


