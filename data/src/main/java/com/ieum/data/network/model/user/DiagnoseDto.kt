package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class DiagnoseDto(
    val name: String,
    val cancerStage: String?,
)
