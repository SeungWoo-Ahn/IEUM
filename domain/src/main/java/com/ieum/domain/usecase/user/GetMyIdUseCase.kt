package com.ieum.domain.usecase.user

import com.ieum.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetMyIdUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
    private val getMyProfileUseCase: GetMyProfileUseCase,
) {
    suspend operator fun invoke(): Result<Int> = runCatching {
        preferenceRepository.getMyId()
            ?: run {
                getMyProfileUseCase().getOrThrow().id
                    .also { preferenceRepository.setMyId(it) }
            }
    }
}