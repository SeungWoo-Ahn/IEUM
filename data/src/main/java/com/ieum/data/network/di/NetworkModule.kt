package com.ieum.data.network.di

import com.ieum.data.network.model.base.ErrorResponse
import com.ieum.data.network.model.base.SGISErrorResponse
import com.ieum.data.network.util.TokenManager
import com.ieum.domain.exception.NetworkException
import com.ieum.domain.exception.SGISException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
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
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.i(message)
                    }
                }
                level = LogLevel.BODY
            }
            install(ContentNegotiation) {
                json(networkJson)
            }
        }

    @Provides
    @Singleton
    @NetworkSource(IEUMNetwork.Default)
    fun providesDefaultClient(
        tokenManager: TokenManager,
    ): HttpClient =
        createKtorClient().config {
            expectSuccess = true
            defaultRequest {
                url("") // TODO: BASE_URL 넣기
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    if (exception !is ResponseException) {
                        return@handleResponseExceptionWithRequest
                    }
                    try {
                        val errorResponse = exception.response.body<ErrorResponse>()
                        when (exception) {
                            is ClientRequestException -> throw NetworkException.ClientSide(errorResponse.message)
                            is ServerResponseException -> throw NetworkException.ServerSide(errorResponse.message)
                            else -> throw NetworkException.Unknown(errorResponse.message)
                        }
                    } catch (e: NoTransformationFoundException) {
                        throw NetworkException.Unknown("error response 직렬화 실패: ${e.message}")
                    } catch (e: Exception) {
                        throw NetworkException.Unknown("알 수 없는 예외 발생: ${e.message}")
                    }
                }
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        tokenManager
                            .getSavedToken()
                            ?.let { token ->
                                BearerTokens(token.accessToken, token.refreshToken)
                            }
                    }
                    refreshTokens {
                        tokenManager
                            .refreshToken(oldTokens?.refreshToken, client)
                            ?.let { token ->
                                BearerTokens(token.accessToken, token.refreshToken)
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
            HttpResponseValidator {
                validateResponse { response ->
                    val errorResponse = response.body<SGISErrorResponse>()
                    if (errorResponse.code != 0) {
                        if (errorResponse.code == -401) {
                            throw SGISException.UnAuthorized(errorResponse.message)
                        } else {
                            throw SGISException.Unknown(errorResponse.message)
                        }
                    }
                }
            }
        }
}