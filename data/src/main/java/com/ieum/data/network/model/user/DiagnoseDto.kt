package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class DiagnoseDto(
    val name: String,
    val cancerStage: String?,
)

enum class DiagnoseDtoKey(val key: String) {
    RENTAL_CANCER("rental_cancer"),
    COLON_CANCER("colon_cancer"),
    LIVER_TRANSPLANT("liver_transplant"),
    OTHERS("others");

    companion object {
        private val map = entries.associateBy(DiagnoseDtoKey::key)

        fun fromKey(key: String): DiagnoseDtoKey = map[key]
            ?: throw IllegalArgumentException("Invalid DiagnoseDtoKey key: $key")
    }
}