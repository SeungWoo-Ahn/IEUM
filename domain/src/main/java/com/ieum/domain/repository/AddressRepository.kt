package com.ieum.domain.repository

import com.ieum.domain.model.address.Address

interface AddressRepository {
    suspend fun getAccessToken(localFirst: Boolean): String

    suspend fun getAddressList(accessToken: String, code: String?): List<Address>
}