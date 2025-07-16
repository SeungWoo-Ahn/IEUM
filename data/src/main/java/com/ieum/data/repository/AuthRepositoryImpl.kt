package com.ieum.data.repository

import com.ieum.data.datasource.auth.AuthDataSource
import com.ieum.data.mapper.asBody
import com.ieum.data.mapper.toDomain
import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.model.auth.OAuthResult
import com.ieum.domain.model.auth.OAuthUser
import com.ieum.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun login(oAuthRequest: OAuthRequest): OAuthResult =
        authDataSource
            .login(oAuthRequest.asBody())
            .toDomain()

    override suspend fun getOAuthUser(): OAuthUser =
        authDataSource
            .getOAuthUser()
            .toDomain()
}