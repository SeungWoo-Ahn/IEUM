package com.ieum.data.network.model.address

import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    val result: List<AddressDto>
)
