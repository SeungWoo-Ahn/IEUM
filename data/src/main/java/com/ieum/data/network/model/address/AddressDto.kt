package com.ieum.data.network.model.address

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("cd") val code: String,
    @SerialName("addr_name") val name: String,
    @SerialName("full_addr") val fullName: String,
)
