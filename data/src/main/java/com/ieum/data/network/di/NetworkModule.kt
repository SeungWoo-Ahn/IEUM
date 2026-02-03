package com.ieum.data.network.di

import com.ieum.data.BuildConfig
import com.ieum.data.mapper.toDomain
import com.ieum.data.network.model.auth.RefreshTokenRequestBody
import com.ieum.data.network.model.auth.RefreshTokenResponse
import com.ieum.data.network.model.base.ErrorResponse
import com.ieum.data.network.model.base.SGISErrorResponse
import com.ieum.domain.exception.NetworkException
import com.ieum.domain.exception.SGISException
import com.ieum.domain.repository.PreferenceRepository
import com.ieum.domain.util.runCatchingExceptCancel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import okio.IOException
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
        HttpClient(OkHttp) {
            engine {
                config {
                    followRedirects(true)
                }
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 5_000
                requestTimeoutMillis = 10_000
                socketTimeoutMillis = 10_000
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
        preferenceRepository: PreferenceRepository,
    ): HttpClient =
        createKtorClient().config {
            expectSuccess = true
            defaultRequest {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            HttpResponseValidator {
                handleResponseException { cause, _ ->
                    when (cause) {
                        is IOException -> throw NetworkException.ConnectionException()
                        is ResponseException -> {
                            val errorResponse = cause.response.body<ErrorResponse>()
                            val message = errorResponse.details?.firstOrNull()?.message
                                ?: errorResponse.message
                            throw NetworkException.ResponseException(message)
                        }
                        else -> throw NetworkException.UnknownException(cause)
                    }
                }
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        preferenceRepository.tokenFlow.first()?.let {
                            BearerTokens(it.accessToken, it.refreshToken)
                        }
                    }
                    refreshTokens {
                        oldTokens?.refreshToken?.let {
                            runCatching {
                                val requestBody = RefreshTokenRequestBody(it)
                                client
                                    .post("api/v1/auth/refresh") {
                                        setBody(requestBody)
                                        markAsRefreshTokenRequest()
                                    }
                                    .body<RefreshTokenResponse>()
                                    .toDomain(it)
                            }.getOrNull()?.let { newToken ->
                                runCatchingExceptCancel { preferenceRepository.saveToken(newToken) }
                                BearerTokens(
                                    accessToken = newToken.accessToken,
                                    refreshToken = newToken.refreshToken
                                )
                            } ?: run {
                                runCatchingExceptCancel { preferenceRepository.clear() }
                                client.authProvider<BearerAuthProvider>()?.clearToken()
                                null
                            }
                        }
                    }
                    sendWithoutRequest { request ->
                        val sendWithoutAuthorization =
                            request.url.encodedPathSegments.contains("auth")
                        sendWithoutAuthorization.not()
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
                            throw SGISException.UnAuthorized()
                        } else {
                            throw SGISException.Unknown()
                        }
                    }
                }
            }
        }
}