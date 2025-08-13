package com.ieum.data.mapper

import com.ieum.data.network.model.user.DiagnoseDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.RegisterRequest

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


