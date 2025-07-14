package com.ieum.domain.usecase.auth

import com.ieum.domain.model.auth.OAuthUser
import com.ieum.domain.repository.AuthRepository
import javax.inject.Inject

class GetOAuthUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<OAuthUser> = runCatching {
        authRepository.getOAuthUser()
    }
}