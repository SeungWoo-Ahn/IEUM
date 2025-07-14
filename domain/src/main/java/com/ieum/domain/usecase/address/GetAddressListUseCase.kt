package com.ieum.domain.usecase.address

import com.ieum.domain.model.address.Address
import com.ieum.domain.repository.AddressRepository
import javax.inject.Inject

class GetAddressListUseCase @Inject constructor(
    private val addressRepository: AddressRepository,
    private val getSGISTokenUseCase: GetSGISTokenUseCase,
) {
    suspend operator fun invoke(code: String): Result<List<Address>> = runCatching {
        getSGISTokenUseCase()
            .getOrThrow()
            .let { accessToken ->
                addressRepository.getAddressList(accessToken, code)
            }
    }
}