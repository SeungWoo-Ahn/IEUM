package com.ieum.domain.usecase.auth

import com.ieum.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String) = runCatching {
        authRepository.login(email)
    }
}