package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class PatchProfileRequestBody(
    val diagnoses: List<DiagnoseDto>? = null,
    val chemotherapy: ChemotherapyDto? = null,
    val radiationTherapy: RadiationTherapyDto? = null,
    val ageGroup: String? = null,
    val residenceArea: String? = null,
    val hospitalArea: String? = null,
    val sexVisible: Boolean? = null,
    val diagnosesVisible: Boolean? = null,
    val chemotherapyVisible: Boolean? = null,
    val radiationTherapyVisible: Boolean? = null,
    val ageGroupVisible: Boolean? = null,
    val residenceAreaVisible: Boolean? = null,
    val hospitalAreaVisible: Boolean? = null,
)
