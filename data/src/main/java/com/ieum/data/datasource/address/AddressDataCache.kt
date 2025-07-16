package com.ieum.data.datasource.address

interface AddressDataCache {
    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String)
}