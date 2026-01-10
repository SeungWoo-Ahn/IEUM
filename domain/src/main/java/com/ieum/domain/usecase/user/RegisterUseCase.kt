package com.ieum.domain.usecase.user

import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.repository.PreferenceRepository
import com.ieum.domain.repository.UserRepository
import com.ieum.domain.util.runCatchingExceptCancel
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(registerRequest: RegisterRequest): Result<Unit> = runCatchingExceptCancel {
        userRepository.register(registerRequest)
        preferenceRepository.savePendingToken()
    }
}