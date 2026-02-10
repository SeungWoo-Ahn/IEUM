package com.ieum.presentation.screen.auth.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.ieum.domain.model.auth.OAuthProvider
import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.presentation.BuildConfig
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
            OAuthRequest(
                provider = provider,
                accessToken = accessToken,
            )
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

    data class Google(
        private val context: Context
    ) : LoginStrategy {
        override val provider: OAuthProvider = OAuthProvider.GOOGLE

        override suspend fun proceed(): Result<OAuthRequest> = runCatching {
            val idToken = getIdToken()
            OAuthRequest(
                accessToken = idToken,
                provider = provider,
            )
        }

        private suspend fun getIdToken(): String {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()
            val credential = CredentialManager
                .create(context)
                .getCredential(context = context, request = request)
                .credential
            if (credential !is CustomCredential || credential.type != TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                throw IllegalArgumentException("mismatch google credential type")
            }
            return GoogleIdTokenCredential.createFrom(credential.data).idToken
        }
    }
}