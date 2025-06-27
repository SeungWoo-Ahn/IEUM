package com.ieum.domain.usecase.auth

import com.ieum.domain.model.auth.JoinRequest
import com.ieum.domain.repository.AuthRepository
import javax.inject.Inject

class JoinUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(joinRequest: JoinRequest) = runCatching {
        authRepository.join(joinRequest)
    }
}