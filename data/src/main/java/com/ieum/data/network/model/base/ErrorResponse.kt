package com.ieum.data.network.model.base

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String,
    val message: String,
    val timestamp: String,
)
