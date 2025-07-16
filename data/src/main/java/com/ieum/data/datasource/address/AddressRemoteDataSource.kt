package com.ieum.data.datasource.address

import com.ieum.data.network.di.IEUMNetwork
import com.ieum.data.network.di.NetworkSource
import com.ieum.data.network.model.address.AddressDto
import com.ieum.data.network.model.address.SGISToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRemoteDataSource @Inject constructor(
    @NetworkSource(IEUMNetwork.Address)
    private val ktorClient: HttpClient,
) : AddressDataSource {
    override suspend fun getSGISToken(): SGISToken =
        ktorClient
            .get("auth/authentication.json") {
                parameter("consumer_key", "")
                parameter("consumer_secret", "") // TODO: 인증키 넣기
            }
            .body<BaseResponse<SGISToken>>()
            .result

    override suspend fun getAddressList(accessToken: String, code: String): List<AddressDto> =
        ktorClient
            .get("addr/stage.json") {
                parameter("accessToken", accessToken)
                parameter("code", code)
            }
            .body<BaseResponse<List<AddressDto>>>()
            .result
}

@Serializable
private data class BaseResponse<T>(
    val result: T
)