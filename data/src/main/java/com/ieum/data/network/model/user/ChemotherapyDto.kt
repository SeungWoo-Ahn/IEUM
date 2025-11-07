package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class ChemotherapyDto(
    val cycle: Int,
    val startDate: String, // TODO: endDate 추가
)
