package com.ieum.data.mapper

import com.ieum.data.TokenEntity
import com.ieum.data.network.model.auth.OAuthResponse
import com.ieum.data.network.model.auth.OAuthUserDto
import com.ieum.data.network.model.auth.RefreshTokenResponse
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.auth.OAuthResult
import com.ieum.domain.model.auth.OAuthUser
import com.ieum.domain.model.auth.Token

fun OAuthUserDto.toDomain(): OAuthUser =
    OAuthUser(
        oauthId = oauthId,
        provider = OAuthProvider.fromKey(provider),
        email = email,
        name = name,
        profileImage = profileImage,
        isRegistered = isRegistered,
    )

fun OAuthResponse.toDomain(): OAuthResult =
    OAuthResult(
        token = Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = expiresIn,
            tokenType = tokenType,
        ),
        oAuthUser = user.toDomain()
    )

fun RefreshTokenResponse.toDomain(refreshToken: String): Token =
    Token(
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresIn = expiresIn,
        tokenType = tokenType,
    )

fun TokenEntity.toDomain(): Token? =
    if (accessToken.isBlank() || refreshToken.isBlank()) {
        null
    } else {
        Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
            tokenType = tokenType,
            expiresIn = expiresIn,
        )
    }

fun Token.toEntity(): TokenEntity =
    TokenEntity
        .newBuilder()
        .apply {
            accessToken = this@toEntity.accessToken
            refreshToken = this@toEntity.refreshToken
            tokenType = this@toEntity.tokenType
            expiresIn = this@toEntity.expiresIn
        }
        .build()