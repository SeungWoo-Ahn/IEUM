package com.ieum.data.datasource.address

import com.ieum.data.network.model.address.AddressDto
import com.ieum.data.network.model.address.SGISToken

interface AddressDataSource {
    suspend fun getSGISToken(): SGISToken

    suspend fun getAddressList(accessToken: String, code: String?): List<AddressDto>
}