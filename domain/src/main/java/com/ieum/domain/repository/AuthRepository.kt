package com.ieum.domain.repository

import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.model.auth.OAuthResult
import com.ieum.domain.model.auth.OAuthUser

interface AuthRepository {
    suspend fun login(oAuthRequest: OAuthRequest): OAuthResult

    suspend fun getOAuthUser(): OAuthUser

    suspend fun refreshToken(): String
}