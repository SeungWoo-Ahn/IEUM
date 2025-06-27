package com.ieum.domain.repository

import com.ieum.domain.model.common.Address
import com.ieum.domain.model.common.SGISToken

interface SGISRepository {
    suspend fun getAccessToken(): SGISToken

    suspend fun getAddressList(accessToken: String, key: String): List<Address>
}