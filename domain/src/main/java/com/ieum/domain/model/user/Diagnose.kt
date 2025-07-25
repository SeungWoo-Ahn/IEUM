package com.ieum.domain.model.user

enum class DiagnoseKey {
    RENTAL_CANCER,
    COLON_CANCER,
    LIVER_TRANSPLANT,
    OTHERS,
}

sealed interface Diagnose {
    val key: DiagnoseKey

    data object LiverTransplant : Diagnose {
        override val key: DiagnoseKey get() = DiagnoseKey.LIVER_TRANSPLANT
    }

    data object Others : Diagnose {
        override val key: DiagnoseKey get() = DiagnoseKey.OTHERS
    }
}

sealed interface CancerDiagnose : Diagnose {
    val cancerStage: CancerStage

    data class ColonCancer(override val cancerStage: CancerStage) : CancerDiagnose {
        override val key: DiagnoseKey get() = DiagnoseKey.COLON_CANCER
    }

    data class RentalCancer(override val cancerStage: CancerStage) : CancerDiagnose {
        override val key: DiagnoseKey get() = DiagnoseKey.RENTAL_CANCER
    }
}