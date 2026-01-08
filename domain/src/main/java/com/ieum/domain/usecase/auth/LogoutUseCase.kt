package com.ieum.domain.usecase.auth

import com.ieum.domain.repository.PreferenceRepository
import com.ieum.domain.util.runCatchingExceptCancel
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(): Result<Unit> = runCatchingExceptCancel {
        preferenceRepository.clear()
    }
}