package com.ieum.data.mapper

import com.ieum.data.network.model.address.AddressDto
import com.ieum.domain.model.address.Address

fun AddressDto.toDomain(): Address =
    Address(
        code = code,
        name = name,
        fullName = fullName,
    )