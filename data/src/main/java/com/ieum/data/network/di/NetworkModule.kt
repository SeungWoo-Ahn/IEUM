package com.ieum.data.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private val networkJson = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    private fun createKtorClient(): HttpClient =
        HttpClient(CIO) {
            install(HttpTimeout) {
                connectTimeoutMillis = 5_000
                requestTimeoutMillis = 5_000
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(networkJson)
            }
        }

    @Provides
    @Singleton
    @NetworkSource(IEUMNetwork.Default)
    fun providesDefaultClient(): HttpClient =
        createKtorClient().config {
            defaultRequest {
                url("") // TODO: BASE_URL 넣기
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        null // TODO: 저장된 토큰 넣기
                    }
                    refreshTokens {
                        oldTokens?.let {
                            null // TODO: refreshToken 로직
                        }
                    }
                    sendWithoutRequest { request ->
                        // TODO: Authorization 필요 없는 api 제외
                        true
                    }
                }
            }
        }

    @Provides
    @Singleton
    @NetworkSource(IEUMNetwork.Address)
    fun providesAddressClient(): HttpClient =
        createKtorClient().config {
            defaultRequest {
                url("https://sgisapi.kostat.go.kr/OpenAPI3/")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
}