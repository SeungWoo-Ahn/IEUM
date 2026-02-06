package com.ieum.domain.usecase.user

import com.ieum.domain.repository.PreferenceRepository
import com.ieum.domain.repository.UserRepository
import com.ieum.domain.util.runCatchingExceptCancel
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(): Result<Unit> = runCatchingExceptCancel {
        userRepository.withdraw()
            .also { preferenceRepository.clear() }
    }
}