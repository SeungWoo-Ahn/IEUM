package com.ieum.domain.model.user

data class PatchProfileRequest(
    val diagnoses: DataStatus<List<Diagnose>>? = null,
    val chemotherapy: DataStatus<Chemotherapy>? = null,
    val radiationTherapy: DataStatus<RadiationTherapy>? = null,
    val ageGroup: DataStatus<AgeGroup>? = null,
    val residenceArea: DataStatus<String>? = null,
    val hospitalArea: DataStatus<String>? = null,
)
