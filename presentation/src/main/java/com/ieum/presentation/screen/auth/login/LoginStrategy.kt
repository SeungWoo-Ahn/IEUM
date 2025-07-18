package com.ieum.presentation.screen.auth.login

import android.content.Context
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.auth.OAuthRequest
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

sealed interface LoginStrategy {
    val provider: OAuthProvider

    suspend fun proceed(): Result<OAuthRequest>

    data class KaKao(
        private val context: Context,
    ) : LoginStrategy {
        override val provider: OAuthProvider = OAuthProvider.KAKAO

        override suspend fun proceed(): Result<OAuthRequest> = runCatching {
            val accessToken = loginWithTalk()
            OAuthRequest(provider, accessToken)
        }

        private suspend fun loginWithTalk(): String =
            suspendCancellableCoroutine { continuation ->
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                        if (error != null) {
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                continuation.cancel(error)
                                return@loginWithKakaoTalk
                            }
                            loginWithAccount(continuation)
                        } else if (token != null) {
                            continuation.resume(token.accessToken)
                        }
                    }
                } else {
                    loginWithAccount(continuation)
                }
            }

        private fun loginWithAccount(continuation: CancellableContinuation<String>) {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    continuation.resume(token.accessToken)
                }
            }
        }
    }
}