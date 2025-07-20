package com.ieum.domain.usecase.address

import com.ieum.domain.exception.SGISException
import com.ieum.domain.model.address.Address
import com.ieum.domain.repository.AddressRepository
import javax.inject.Inject

class GetAddressListUseCase @Inject constructor(
    private val addressRepository: AddressRepository,
) {
    suspend operator fun invoke(code: String? = null): Result<List<Address>> = runCatching {
        getAddressList(code, localFirst = true)
            .getOrElse { t ->
                when (t) {
                    is SGISException.UnAuthorized -> getAddressList(code, localFirst = false).getOrThrow()
                    else -> throw t
                }
            }
    }

    private suspend fun getAddressList(code: String?, localFirst: Boolean): Result<List<Address>> =
        runCatching {
            val accessToken = addressRepository.getAccessToken(localFirst = localFirst)
            addressRepository.getAddressList(accessToken, code)
        }
}