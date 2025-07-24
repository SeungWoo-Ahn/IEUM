package com.ieum.domain.usecase.auth

import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.repository.AuthRepository
import com.ieum.domain.repository.PreferenceRepository
import com.ieum.domain.util.runCatchingExceptCancel
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(oAuthRequest: OAuthRequest): Result<Boolean> =
        runCatchingExceptCancel {
            val (token, oAuthUser) = authRepository.login(oAuthRequest)
            preferenceRepository.saveToken(token)
            oAuthUser.isRegistered
        }
}