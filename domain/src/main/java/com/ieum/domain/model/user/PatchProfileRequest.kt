package com.ieum.domain.model.user

data class PatchProfileRequest(
    val diagnoses: ProfileProperty<List<Diagnose>>,
    val surgery: ProfileProperty<List<Surgery>>,
    val chemotherapy: ProfileProperty<List<Chemotherapy>>,
    val radiationTherapy: ProfileProperty<List<RadiationTherapy>>,
    val ageGroup: ProfileProperty<AgeGroup>,
    val residenceArea: ProfileProperty<String>,
    val hospitalArea: ProfileProperty<String>,
)
