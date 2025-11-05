package com.ieum.data.mapper

import com.ieum.data.network.model.user.ChemotherapyDto
import com.ieum.data.network.model.user.DiagnoseDto
import com.ieum.data.network.model.user.MyProfileDto
import com.ieum.data.network.model.user.OthersProfileDto
import com.ieum.data.network.model.user.RadiationTherapyDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Chemotherapy
import com.ieum.domain.model.user.DataVisibility
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.model.user.Profile
import com.ieum.domain.model.user.RadiationTherapy
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.Sex
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
        Diagnosis.RECTAL_CANCER -> CancerDiagnose.RectalCancer(CancerStage.fromKey(requireNotNull(cancerStage)))
        Diagnosis.COLON_CANCER -> CancerDiagnose.ColonCancer(CancerStage.fromKey(requireNotNull(cancerStage)))
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

private fun ChemotherapyDto.toDomain(): Chemotherapy =
    Chemotherapy(
        cycle = cycle,
        startDate = startDate,
    )

private fun RadiationTherapyDto.toDomain(): RadiationTherapy =
    RadiationTherapy(
        startDate = startDate,
        endDate = endDate,
    )

fun MyProfileDto.toDomain(): Profile =
    Profile(
        id = id,
        oAuthProvider = OAuthProvider.fromKey(oauthProvider),
        email = email,
        userType = UserType.fromKey(userType),
        nickname = nickname,
        sex = Sex.fromKey(sex)
            .let { if (sexVisible) DataVisibility.Open(it) else DataVisibility.Hide(it) },
        diagnoses = diagnoses.map(DiagnoseDto::toDomain)
            .let { if (diagnosesVisible) DataVisibility.Open(it) else DataVisibility.Hide(it) },
        chemotherapy = chemotherapy?.toDomain()
            ?.let { if (chemotherapyVisible) DataVisibility.Open(it) else DataVisibility.Hide(it) },
        radiationTherapy = radiationTherapy?.toDomain()
            ?.let { if (radiationTherapyVisible) DataVisibility.Open(it) else DataVisibility.Hide(it) },
        ageGroup = ageGroup?.let { AgeGroup.fromKey(it) }
            ?.let { if (ageGroupVisible) DataVisibility.Open(it) else DataVisibility.Hide(it) },
        residenceArea = residenceArea
            ?.let { if (residenceAreaVisible) DataVisibility.Open(it) else DataVisibility.Hide(it) },
        hospitalArea = hospitalArea
            ?.let { if (hospitalAreaVisible) DataVisibility.Open(it) else DataVisibility.Hide(it) },
    )

fun OthersProfileDto.toDomain(): Profile =
    Profile(
        id = id,
        oAuthProvider = OAuthProvider.fromKey(oauthProvider),
        email = null,
        userType = UserType.fromKey(userType),
        nickname = nickname,
        sex = if (sexVisible) {
            DataVisibility.Open(
                Sex.fromKey(requireNotNull(sex))
            )
        } else {
            DataVisibility.None
        },
        diagnoses = if (diagnosesVisible) {
            DataVisibility.Open(
                requireNotNull(diagnoses).map(DiagnoseDto::toDomain)
            )
        } else {
            DataVisibility.None
        },
        chemotherapy = if (chemotherapyVisible) {
            chemotherapy?.let { DataVisibility.Open(it.toDomain()) }
        } else {
            DataVisibility.None
        },
        radiationTherapy = if (radiationTherapyVisible) {
            radiationTherapy?.let { DataVisibility.Open(it.toDomain()) }
        } else {
            DataVisibility.None
        },
        ageGroup = if (ageGroupVisible) {
            ageGroup?.let { DataVisibility.Open(AgeGroup.fromKey(it)) }
        } else {
            DataVisibility.None
        },
        residenceArea = if (residenceAreaVisible) {
            residenceArea?.let { DataVisibility.Open(it) }
        } else {
            DataVisibility.None
        },
        hospitalArea = if (hospitalAreaVisible) {
            hospitalArea?.let { DataVisibility.Open(it) }
        } else {
            DataVisibility.None
        },
    )


