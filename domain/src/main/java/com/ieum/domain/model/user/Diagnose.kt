package com.ieum.domain.model.user

sealed interface Diagnose

interface CancerDiagnose : Diagnose {
    val cancerStage: CancerStage?
}

data class RentalCancer(override val cancerStage: CancerStage?) : CancerDiagnose

data class ColonCancer(override val cancerStage: CancerStage?) : CancerDiagnose

data object LiverTransplant : Diagnose

data object Others : Diagnose