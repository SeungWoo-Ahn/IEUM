package com.ieum.data.mapper

import com.ieum.data.network.model.user.ChemotherapyDto
import com.ieum.data.network.model.user.DiagnoseDto
import com.ieum.data.network.model.user.MyProfileDto
import com.ieum.data.network.model.user.OthersProfileDto
import com.ieum.data.network.model.user.PatchProfileRequestBody
import com.ieum.data.network.model.user.RadiationTherapyDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Chemotherapy
import com.ieum.domain.model.user.DataStatus
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.model.user.OthersProfile
import com.ieum.domain.model.user.PatchProfileRequest
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

private fun Chemotherapy.toDto(): ChemotherapyDto =
    ChemotherapyDto(
        cycle = cycle,
        startDate = startDate,
    )

private fun ChemotherapyDto.toDomain(): Chemotherapy =
    Chemotherapy(
        cycle = cycle,
        startDate = startDate,
    )

private fun RadiationTherapy.toDto(): RadiationTherapyDto =
    RadiationTherapyDto(
        startDate = startDate,
        endDate = endDate,
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
            .let { if (sexVisible) DataStatus.Open(it) else DataStatus.Hide(it) },
        diagnoses = diagnoses.map(DiagnoseDto::toDomain)
            .let { if (diagnosesVisible) DataStatus.Open(it) else DataStatus.Hide(it) },
        chemotherapy = when {
            chemotherapy == null -> DataStatus.None
            chemotherapyVisible -> DataStatus.Open(chemotherapy.toDomain())
            else -> DataStatus.Hide(chemotherapy.toDomain())
        },
        radiationTherapy = when {
            radiationTherapy == null -> DataStatus.None
            radiationTherapyVisible -> DataStatus.Open(radiationTherapy.toDomain())
            else -> DataStatus.Hide(radiationTherapy.toDomain())
        },
        ageGroup = when {
            ageGroup == null -> DataStatus.None
            ageGroupVisible -> DataStatus.Open(AgeGroup.fromKey(ageGroup))
            else -> DataStatus.Hide(AgeGroup.fromKey(ageGroup))
        },
        residenceArea = when {
            residenceArea == null -> DataStatus.None
            residenceAreaVisible -> DataStatus.Open(residenceArea)
            else -> DataStatus.Hide(residenceArea)
        },
        hospitalArea = when {
            hospitalArea == null -> DataStatus.None
            hospitalAreaVisible -> DataStatus.Open(hospitalArea)
            else -> DataStatus.Hide(hospitalArea)
        },
    )

fun OthersProfileDto.toDomain(): OthersProfile =
    OthersProfile(
        id = id,
        oAuthProvider = OAuthProvider.fromKey(oauthProvider),
        userType = UserType.fromKey(userType),
        nickname = nickname,
        sex = if (sexVisible && sex != null) Sex.fromKey(sex) else null,
        diagnoses = if (diagnosesVisible && diagnoses != null) {
            diagnoses.map(DiagnoseDto::toDomain)
        } else {
            null
        },
        chemotherapy = if (chemotherapyVisible && chemotherapy != null) {
            chemotherapy.toDomain()
        } else {
            null
        },
        radiationTherapy = if (radiationTherapyVisible && radiationTherapy != null) {
            radiationTherapy.toDomain()
        } else {
            null
        },
        ageGroup = if (ageGroupVisible && ageGroup != null) AgeGroup.fromKey(ageGroup) else null,
        residenceArea = if (residenceAreaVisible && residenceArea != null) residenceArea else null,
        hospitalArea = if (hospitalAreaVisible && hospitalArea != null) hospitalArea else null,
    )

private fun <T> DataStatus<T>?.split(): Pair<T?, Boolean?> = when (this) {
    null, DataStatus.None -> null to null
    is DataStatus.Open<T> -> data to true
    is DataStatus.Hide<T> -> data to false
}

fun PatchProfileRequest.asBody(): PatchProfileRequestBody {
    val (diagnoses, diagnosesVisible) = diagnoses.split()
    val (chemotherapy, chemotherapyVisible) = chemotherapy.split()
    val (radiationTherapy, radiationTherapyVisible) = radiationTherapy.split()
    val (ageGroup, ageGroupVisible) = ageGroup.split()
    val (residenceArea, residenceAreaVisible) = residenceArea.split()
    val (hospitalArea, hospitalAreaVisible) = hospitalArea.split()
    return PatchProfileRequestBody(
        diagnoses = diagnoses?.map(Diagnose::toDto),
        diagnosesVisible = diagnosesVisible,
        chemotherapy = chemotherapy?.toDto(),
        chemotherapyVisible = chemotherapyVisible,
        radiationTherapy = radiationTherapy?.toDto(),
        radiationTherapyVisible = radiationTherapyVisible,
        ageGroup = ageGroup?.key,
        ageGroupVisible = ageGroupVisible,
        residenceArea = residenceArea,
        residenceAreaVisible = residenceAreaVisible,
        hospitalArea = hospitalArea,
        hospitalAreaVisible = hospitalAreaVisible,
    )
}


