package com.ieum.domain.repository

import com.ieum.domain.model.auth.Token
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val tokenFlow: Flow<Token?>

    suspend fun saveToken(token: Token)

    fun setPendingToken(token: Token)

    suspend fun savePendingToken()

    fun getMyId(): Int?

    fun setMyId(id: Int)

    suspend fun clear()
}