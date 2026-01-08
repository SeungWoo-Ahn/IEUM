package com.ieum.domain.usecase.auth

import com.ieum.domain.model.auth.Token
import com.ieum.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenFlowUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
) {
    operator fun invoke(): Flow<Token?> = preferenceRepository.tokenFlow
}