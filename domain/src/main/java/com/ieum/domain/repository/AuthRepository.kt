package com.ieum.domain.repository

import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.model.auth.OAuthResult

interface AuthRepository {
    suspend fun login(request: OAuthRequest): OAuthResult

    suspend fun healthCheck()
}