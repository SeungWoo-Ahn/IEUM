package com.ieum.domain.repository

import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.User

interface UserRepository {
    suspend fun register(registerRequest: RegisterRequest)

    suspend fun getMyProfile(): User
}