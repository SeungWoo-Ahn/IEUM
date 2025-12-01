package com.ieum.domain.model.user

data class PatchProfileRequest(
    val diagnoses: ProfileProperty<List<Diagnose>>,
    val chemotherapy: ProfileProperty<Chemotherapy>,
    val radiationTherapy: ProfileProperty<RadiationTherapy>,
    val ageGroup: ProfileProperty<AgeGroup>,
    val residenceArea: ProfileProperty<String>,
    val hospitalArea: ProfileProperty<String>,
)
