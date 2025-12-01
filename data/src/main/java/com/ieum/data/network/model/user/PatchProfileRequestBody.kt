package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class PatchProfileRequestBody(
    val diagnoses: List<DiagnoseDto>?,
    val chemotherapy: ChemotherapyDto?,
    val radiationTherapy: RadiationTherapyDto?,
    val ageGroup: String?,
    val residenceArea: String?,
    val hospitalArea: String?,
    val diagnosesVisible: Boolean?,
    val chemotherapyVisible: Boolean?,
    val radiationTherapyVisible: Boolean?,
    val ageGroupVisible: Boolean?,
    val residenceAreaVisible: Boolean?,
    val hospitalAreaVisible: Boolean?,
)
