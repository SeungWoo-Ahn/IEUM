package com.ieum.presentation.screen.auth.login

import android.content.Context
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.auth.OAuthRequest

sealed interface LoginStrategy {
    val provider: OAuthProvider

    fun proceed(): Result<OAuthRequest>

    data class KaKao(
        private val context: Context,
    ) : LoginStrategy {
        override val provider: OAuthProvider = OAuthProvider.KAKAO

        override fun proceed(): Result<OAuthRequest> {
            TODO("Not yet implemented")
        }
    }
}