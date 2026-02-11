package com.ieum.data.repository

import com.ieum.data.datasource.auth.AuthDataSource
import com.ieum.data.mapper.toDomain
import com.ieum.data.network.model.auth.OAuthRequestBody
import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.model.auth.OAuthResult
import com.ieum.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun login(request: OAuthRequest): OAuthResult =
        authDataSource
            .login(
                provider = request.provider.key,
                requestBody = OAuthRequestBody(request.accessToken)
            )
            .toDomain()

    override suspend fun healthCheck() {
        authDataSource.ping()
    }
}