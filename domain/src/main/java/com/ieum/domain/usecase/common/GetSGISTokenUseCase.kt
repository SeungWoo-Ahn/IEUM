package com.ieum.domain.usecase.common

import com.ieum.domain.repository.SGISRepository
import javax.inject.Inject

class GetSGISTokenUseCase @Inject constructor(
    private val sgisRepository: SGISRepository,
) {
    suspend operator fun invoke() = runCatching {
        sgisRepository.getAccessToken()
    }
}