package com.ieum.domain.usecase.auth

import com.ieum.domain.repository.AuthRepository
import javax.inject.Inject

class HealthCheckUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        authRepository.healthCheck()
    }
}