package com.ieum.domain.model.auth

import com.ieum.domain.model.base.KeyAble

enum class OAuthProvider(override val key: String) : KeyAble<String> {
    KAKAO("kakao"),
    GOOGLE("google");

    companion object {
        private val map = entries.associateBy(OAuthProvider::key)

        fun fromKey(key: String): OAuthProvider = map[key]
            ?: throw IllegalArgumentException("Invalid OAuthProvider key: $key")
    }
}