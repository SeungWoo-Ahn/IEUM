package com.ieum.domain.repository

import com.ieum.domain.model.auth.JoinRequest

interface AuthRepository {
    suspend fun login(email: String)

    suspend fun join(joinRequest: JoinRequest)
}