package com.ieum.domain.usecase.address

import com.ieum.domain.repository.AddressRepository
import javax.inject.Inject

class GetSGISTokenUseCase @Inject constructor(
    private val addressRepository: AddressRepository,
) {
    suspend operator fun invoke(): Result<String> = runCatching {
        addressRepository.getAccessToken()
    }
}