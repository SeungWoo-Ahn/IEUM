package com.ieum.domain.model.user

import com.ieum.domain.model.base.KeyAble

enum class Diagnosis(override val key: String) : KeyAble<String> {
    COLON_CANCER("colon_cancer"),
    RECTAL_CANCER("rectal_cancer"),
    LIVER_TRANSPLANT("liver_transplant"),
    OTHERS("others");

    companion object {
        private val map = entries.associateBy(Diagnosis::key)

        fun fromKey(key: String): Diagnosis = map[key]
            ?: throw IllegalArgumentException("Invalid Diagnosis key: $key")
    }
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

    data class RectalCancer(override val cancerStage: CancerStage) : CancerDiagnose {
        override val name: Diagnosis get() = Diagnosis.RECTAL_CANCER
    }
}