package com.ieum.domain.model.user

import com.ieum.domain.model.base.KeyAble

enum class Diagnosis(override val key: String) : KeyAble<String> {
    RECTAL_CANCER("rectal_cancer"),
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

    data class RectalCancer(override val cancerStage: CancerStage) : CancerDiagnose {
        override val name: Diagnosis get() = Diagnosis.RECTAL_CANCER
    }

    data class ColonCancer(override val cancerStage: CancerStage) : CancerDiagnose {
        override val name: Diagnosis get() = Diagnosis.COLON_CANCER
    }
}