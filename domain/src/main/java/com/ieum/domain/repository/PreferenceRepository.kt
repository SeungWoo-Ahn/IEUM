package com.ieum.domain.repository

import com.ieum.domain.model.auth.Token
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val tokenFlow: Flow<Token?>

    suspend fun saveToken(token: Token)

    suspend fun clearToken()
}