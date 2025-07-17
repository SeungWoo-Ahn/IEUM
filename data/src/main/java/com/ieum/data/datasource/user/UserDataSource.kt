package com.ieum.data.datasource.user

import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.UserDto

interface UserDataSource {
    suspend fun register(registerRequestBody: RegisterRequestBody)

    suspend fun getMyProfile(): UserDto
}