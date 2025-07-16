package com.ieum.data.datasource.auth

import com.ieum.data.network.model.auth.OAuthRequestBody
import com.ieum.data.network.model.auth.OAuthResponse
import com.ieum.data.network.model.auth.OAuthUserDto

interface AuthDataSource {
    suspend fun login(oAuthRequestBody: OAuthRequestBody): OAuthResponse

    suspend fun getOAuthUser(): OAuthUserDto
}