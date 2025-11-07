package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class RadiationTherapyDto(
    val startDate: String,
    val endDate: String?,
)
