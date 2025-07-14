package com.ieum.domain.usecase.user

import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(registerRequest: RegisterRequest): Result<Unit> = runCatching {
        userRepository.register(registerRequest)
    }
}