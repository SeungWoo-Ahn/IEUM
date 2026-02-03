package com.ieum.data.network.model.base

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDetail(
    val message: String
)
