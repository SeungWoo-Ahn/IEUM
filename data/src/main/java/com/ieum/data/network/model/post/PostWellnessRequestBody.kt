package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class PostWellnessRequestBody(
    val diagnosis: List<String>?,
    val mood: Int,
    val unusualSymptoms: String?,
    val medicationTaken: Boolean,
    val diet: DietDto?,
    val memo: String?,
    val shared: Boolean,
)
