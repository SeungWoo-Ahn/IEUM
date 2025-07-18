package com.ieum.presentation.screen.auth.login

import android.content.Context
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.auth.OAuthRequest
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

sealed interface LoginStrategy {
    val provider: OAuthProvider

    fun proceed(): Result<OAuthRequest>

    data class KaKao(
        private val context: Context,
    ) : LoginStrategy {
        override val provider: OAuthProvider = OAuthProvider.KAKAO

        override fun proceed(): Result<OAuthRequest> = runCatching {
            var accessTokenResult: Result<String>? = null
            loginWithKakao(
                onSuccess = { accessToken ->
                    accessTokenResult = Result.success(accessToken)
                },
                onFailure = { t ->
                    accessTokenResult = Result.failure(t)
                }
            )
            val accessToken = accessTokenResult?.getOrThrow()
                ?: throw IllegalStateException("카카오 로그인 취소")
            OAuthRequest(provider, accessToken)
        }

        private fun loginWithKakao(
            onSuccess: (String) -> Unit,
            onFailure: (Throwable) -> Unit,
        ) {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        loginWithKakaoAccount(onSuccess, onFailure)
                    } else if (token != null) {
                        onSuccess(token.accessToken)
                    }
                }
            } else {
                loginWithKakaoAccount(onSuccess, onFailure)
            }
        }

        private fun loginWithKakaoAccount(
            onSuccess: (String) -> Unit,
            onFailure: (Throwable) -> Unit,
        ) {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    onFailure(error)
                } else if (token != null) {
                    onSuccess(token.accessToken)
                }
            }
        }
    }
}