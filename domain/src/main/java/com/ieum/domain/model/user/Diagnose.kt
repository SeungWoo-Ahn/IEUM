package com.ieum.domain.model.user

import com.ieum.domain.model.base.KeyAble

enum class Diagnosis(override val key: String) : KeyAble<String> {
    RENTAL_CANCER("rental_cancer"),
    COLON_CANCER("colon_cancer"),
    LIVER_TRANSPLANT("liver_transplant"),
    OTHERS("others");
}

sealed interface Diagnose {
    val name: Diagnosis

    data object LiverTransplant : Diagnose {
        override val name: Diagnosis get() = Diagnosis.LIVER_TRANSPLANT
    }

    data object Others : Diagnose {
        override val name: Diagnosis get() = Diagnosis.OTHERS
    }
}

sealed interface CancerDiagnose : Diagnose {
    val cancerStage: CancerStage

    data class ColonCancer(override val cancerStage: CancerStage) : CancerDiagnose {
        override val name: Diagnosis get() = Diagnosis.COLON_CANCER
    }

    data class RentalCancer(override val cancerStage: CancerStage) : CancerDiagnose {
        override val name: Diagnosis get() = Diagnosis.RENTAL_CANCER
    }
}