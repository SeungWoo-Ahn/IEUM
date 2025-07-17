package com.ieum.domain.usecase.address

import com.ieum.domain.model.address.Address
import com.ieum.domain.repository.AddressRepository
import javax.inject.Inject

class GetAddressListUseCase @Inject constructor(
    private val addressRepository: AddressRepository,
) {
    suspend operator fun invoke(code: String? = null): Result<List<Address>> = runCatching {
        val accessToken = addressRepository.getAccessToken()
        addressRepository.getAddressList(accessToken, code)
    }
}