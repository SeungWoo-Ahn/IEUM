package com.ieum.data.network.model.address

import kotlinx.serialization.Serializable

@Serializable
data class AddressTokenResponse(
    val result: SGISToken,
)
