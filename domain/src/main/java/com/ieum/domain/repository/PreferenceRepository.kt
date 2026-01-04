package com.ieum.domain.repository

import com.ieum.domain.model.auth.Token
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val tokenFlow: Flow<Token?>

    fun getCachedToken(): Token?

    suspend fun saveToken(token: Token)

    suspend fun clearToken()

    fun getMyId(): Int?

    fun setMyId(id: Int)
}