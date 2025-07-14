package com.ieum.domain.model.user

sealed interface Diagnose {
    data class RentalCancer(val cancerStage: CancerStage) : Diagnose

    data class ColonCancer(val cancerStage: CancerStage) : Diagnose

    data object LiverTransplant : Diagnose

    data object Others : Diagnose
}