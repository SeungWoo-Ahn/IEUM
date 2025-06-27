package com.ieum.domain.usecase.common

import com.ieum.domain.repository.SGISRepository
import javax.inject.Inject

class GetAddressListUseCase @Inject constructor(
    private val sgisRepository: SGISRepository,
    private val getSGISTokenUseCase: GetSGISTokenUseCase,
) {
    suspend operator fun invoke(key: String) = runCatching {
        getSGISTokenUseCase()
            .getOrThrow()
            .let { token ->
                sgisRepository.getAddressList(token.accessToken, key)
            }
    }
}