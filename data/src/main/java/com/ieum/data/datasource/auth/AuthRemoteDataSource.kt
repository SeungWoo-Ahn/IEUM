package com.ieum.data.datasource.auth

import com.ieum.data.network.di.IEUMNetwork
import com.ieum.data.network.di.NetworkSource
import com.ieum.data.network.model.auth.OAuthRequestBody
import com.ieum.data.network.model.auth.OAuthResponse
import com.ieum.data.network.model.auth.OAuthUserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(
    @NetworkSource(IEUMNetwork.Default)
    private val ktorClient: HttpClient,
) : AuthDataSource {
    override suspend fun login(oAuthRequestBody: OAuthRequestBody): OAuthResponse =
        ktorClient
            .post("api/v1/auth/oauth/callback") {
                setBody(oAuthRequestBody)
            }
            .body<OAuthResponse>()

    override suspend fun getOAuthUser(): OAuthUserDto =
        ktorClient
            .get("api/v1/auth/me")
            .body<OAuthUserDto>()
}