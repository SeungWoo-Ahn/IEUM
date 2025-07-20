package com.ieum.data.network.model.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SGISErrorResponse(
    @SerialName("errCd") val code: Int,
    @SerialName("errMsg") val message: String,
)
