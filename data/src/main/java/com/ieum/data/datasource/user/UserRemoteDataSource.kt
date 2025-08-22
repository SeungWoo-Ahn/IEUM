package com.ieum.data.datasource.user

import com.ieum.data.network.di.IEUMNetwork
import com.ieum.data.network.di.NetworkSource
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    @NetworkSource(IEUMNetwork.Default)
    private val ktorClient: HttpClient,
) : UserDataSource {
    override suspend fun register(registerRequestBody: RegisterRequestBody) {
        ktorClient
            .post("api/v1/users/register") {
                setBody(registerRequestBody)
            }
    }


    override suspend fun getMyProfile(): UserDto =
        ktorClient
            .get("api/v1/users/profile")
            .body<UserDto>()

    override suspend fun getOthersProfile(id: Int): UserDto =
        ktorClient
            .get("api/v1/users/$id/profile")
            .body<UserDto>()
}