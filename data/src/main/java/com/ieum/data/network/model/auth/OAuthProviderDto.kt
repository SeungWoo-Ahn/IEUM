package com.ieum.data.network.model.auth

enum class OAuthProviderDto(val key: String) {
    KAKAO("kakao");

    companion object {
        private val map = entries.associateBy(OAuthProviderDto::key)

        fun fromKey(key: String): OAuthProviderDto = map[key]
            ?: throw IllegalArgumentException("Invalid OAuthProviderDto key: $key")
    }
}