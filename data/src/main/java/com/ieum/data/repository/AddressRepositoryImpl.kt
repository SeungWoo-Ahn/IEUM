package com.ieum.data.repository

import com.ieum.data.datasource.address.AddressDataCache
import com.ieum.data.datasource.address.AddressDataSource
import com.ieum.data.mapper.toDomain
import com.ieum.data.network.model.address.AddressDto
import com.ieum.domain.model.address.Address
import com.ieum.domain.repository.AddressRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepositoryImpl @Inject constructor(
    private val addressDataSource: AddressDataSource,
    private val addressDataCache: AddressDataCache,
) : AddressRepository {
    override suspend fun getAccessToken(localFirst: Boolean): String =
        if (localFirst) {
            addressDataCache.getAccessToken() ?: getRemoteAccessToken()
        } else {
            getRemoteAccessToken()
        }

    private suspend fun getRemoteAccessToken(): String =
        addressDataSource
            .getSGISToken()
            .accessToken
            .also {
                addressDataCache.setAccessToken(it)
            }

    override suspend fun getAddressList(accessToken: String, code: String?): List<Address> =
        addressDataSource
            .getAddressList(accessToken, code)
            .map(AddressDto::toDomain)
}