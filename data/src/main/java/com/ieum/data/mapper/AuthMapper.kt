package com.ieum.data.mapper

import com.ieum.data.network.model.auth.OAuthRequestBody
import com.ieum.data.network.model.auth.OAuthResponse
import com.ieum.data.network.model.auth.OAuthUserDto
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.model.auth.OAuthUser
import com.ieum.domain.model.auth.Token

internal object OAuthProviderMapper : KeyAble<OAuthProvider> {
    private const val KAKAO_KEY = "kakao"

    override fun toKey(value: OAuthProvider): String {
        return when (value) {
            OAuthProvider.KAKAO -> KAKAO_KEY
        }
    }

    override fun fromKey(key: String): OAuthProvider {
        return when (key) {
            KAKAO_KEY -> OAuthProvider.KAKAO
            else -> throw IllegalArgumentException("Invalid OAuthProvider key: $key")
        }
    }
}

fun OAuthRequest.asBody(): OAuthRequestBody =
    OAuthRequestBody(
        provider = OAuthProviderMapper.toKey(provider),
        authorizationCode = code,
    )

fun OAuthUserDto.toDomain(): OAuthUser =
    OAuthUser(
        oauthId = oauthId,
        provider = OAuthProviderMapper.fromKey(provider),
        email = email,
        name = name,
        profileImage = profileImage,
        isRegistered = isRegistered,
    )

fun OAuthResponse.toDomain(): Token =
    Token(
        accessToken = accessToken,
        refreshToken = refreshToken,
        tokenType = tokenType,
        expiresIn = expiresIn,
        user = user.toDomain()
    )