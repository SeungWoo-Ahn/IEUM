package com.ieum.data.network.util

import com.ieum.data.mapper.toDomain
import com.ieum.data.network.model.auth.RefreshTokenRequestBody
import com.ieum.data.network.model.auth.RefreshTokenResponse
import com.ieum.domain.model.auth.Token
import com.ieum.domain.repository.PreferenceRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
) {
    suspend fun getSavedToken(): Token? =
        preferenceRepository
            .tokenFlow
            .first()

    suspend fun refreshToken(originRefreshToken: String?, client: HttpClient): Token? {
        val refreshToken = originRefreshToken ?: getSavedToken()?.refreshToken
        return refreshToken?.let {
            try {
                val requestBody = RefreshTokenRequestBody(it)
                val newToken =
                    client
                        .post("api/v1/auth/refresh") {
                            setBody(requestBody)
                        }
                        .body<RefreshTokenResponse>()
                        .toDomain(it)
                preferenceRepository.saveToken(newToken)
                newToken
            } catch (_: Exception) {
                preferenceRepository.clearToken()
                client.authProvider<BearerAuthProvider>()?.clearToken()
                null
            }
        }
    }
}