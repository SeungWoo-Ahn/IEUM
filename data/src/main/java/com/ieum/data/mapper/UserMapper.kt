package com.ieum.data.mapper

import com.ieum.data.network.model.user.ChemotherapyDto
import com.ieum.data.network.model.user.DiagnoseDto
import com.ieum.data.network.model.user.MyProfileDto
import com.ieum.data.network.model.user.OthersProfileDto
import com.ieum.data.network.model.user.PatchProfileRequestBody
import com.ieum.data.network.model.user.RadiationTherapyDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.SurgeryDto
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Chemotherapy
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.model.user.MyProfile
import com.ieum.domain.model.user.OthersProfile
import com.ieum.domain.model.user.PatchProfileRequest
import com.ieum.domain.model.user.ProfileProperty
import com.ieum.domain.model.user.RadiationTherapy
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.Sex
import com.ieum.domain.model.user.Surgery
import com.ieum.domain.model.user.UserType

private fun Diagnose.toDto(): DiagnoseDto =
    when(this) {
        is CancerDiagnose -> DiagnoseDto(
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

private fun Surgery.toDto(): SurgeryDto =
    SurgeryDto(
        date = date,
        description = description,
    )

private fun SurgeryDto.toDomain(): Surgery =
    Surgery(
        date = date,
        description = description,
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

fun MyProfileDto.toDomain(): MyProfile =
    MyProfile(
        id = id,
        oAuthProvider = OAuthProvider.fromKey(oauthProvider),
        email = email,
        userType = UserType.fromKey(userType),
        nickname = nickname,
        sex = ProfileProperty(data = Sex.fromKey(sex), open = sexVisible),
        diagnoses = ProfileProperty(
            data = diagnoses.map(DiagnoseDto::toDomain),
            open = diagnosesVisible
        ),
        surgery = ProfileProperty(
            data = surgery?.map(SurgeryDto::toDomain),
            open = surgeryVisible
        ),
        chemotherapy = ProfileProperty(
            data = chemotherapy?.map(ChemotherapyDto::toDomain),
            open = chemotherapyVisible
        ),
        radiationTherapy = ProfileProperty(
            data = radiationTherapy?.map(RadiationTherapyDto::toDomain),
            open = radiationTherapyVisible
        ),
        ageGroup = ProfileProperty(
            data = ageGroup?.let { AgeGroup.fromKey(it) },
            open = ageGroupVisible
        ),
        residenceArea = ProfileProperty(data = residenceArea, open = residenceAreaVisible),
        hospitalArea = ProfileProperty(data = hospitalArea, open = hospitalAreaVisible),
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
        surgery = if (surgeryVisible && surgery != null) {
            surgery.map(SurgeryDto::toDomain)
        } else {
            null
        },
        chemotherapy = if (chemotherapyVisible && chemotherapy != null) {
            chemotherapy.map(ChemotherapyDto::toDomain)
        } else {
            null
        },
        radiationTherapy = if (radiationTherapyVisible && radiationTherapy != null) {
            radiationTherapy.map(RadiationTherapyDto::toDomain)
        } else {
            null
        },
        ageGroup = if (ageGroupVisible && ageGroup != null) AgeGroup.fromKey(ageGroup) else null,
        residenceArea = if (residenceAreaVisible && residenceArea != null) residenceArea else null,
        hospitalArea = if (hospitalAreaVisible && hospitalArea != null) hospitalArea else null,
    )

fun PatchProfileRequest.asBody(): PatchProfileRequestBody {
    return PatchProfileRequestBody(
        diagnoses = diagnoses.data?.map(Diagnose::toDto),
        diagnosesVisible = diagnoses.open,
        surgery = surgery.data?.map(Surgery::toDto),
        surgeryVisible = surgery.open,
        chemotherapy = chemotherapy.data?.map(Chemotherapy::toDto),
        chemotherapyVisible = chemotherapy.open,
        radiationTherapy = radiationTherapy.data?.map(RadiationTherapy::toDto),
        radiationTherapyVisible = radiationTherapy.open,
        ageGroup = ageGroup.data?.key,
        ageGroupVisible = ageGroup.open,
        residenceArea = residenceArea.data,
        residenceAreaVisible = residenceArea.open,
        hospitalArea = hospitalArea.data,
        hospitalAreaVisible = hospitalArea.open,
    )
}


