package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class SurgeryDto(
    val date: String,
    val description: String,
)
