package com.ieum.data.datasource.address

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressLocalDataCache @Inject constructor() : AddressDataCache {
    private val _accessToken = MutableStateFlow<String?>(null)

    override fun getAccessToken(): String? {
        return _accessToken.value
    }

    override fun setAccessToken(accessToken: String) {
        _accessToken.update { accessToken }
    }
}