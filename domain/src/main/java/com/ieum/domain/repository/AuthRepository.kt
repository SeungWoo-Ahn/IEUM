package com.ieum.domain.repository

import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.model.auth.OAuthUser
import com.ieum.domain.model.auth.Token

interface AuthRepository {
    suspend fun login(oAuthRequest: OAuthRequest): Token

    suspend fun getOAuthUser(): OAuthUser

    suspend fun refreshToken(): String
}