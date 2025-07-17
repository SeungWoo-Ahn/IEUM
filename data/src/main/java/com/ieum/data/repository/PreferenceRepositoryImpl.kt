package com.ieum.data.repository

import androidx.datastore.core.DataStore
import com.ieum.domain.model.auth.Token
import com.ieum.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val tokenDataStore: DataStore<Token?>,
) : PreferenceRepository {
    override val tokenFlow: Flow<Token?> get() = tokenDataStore.data

    override suspend fun saveToken(token: Token) {
        tokenDataStore.updateData { token }
    }

    override suspend fun clearToken() {
        tokenDataStore.updateData { null }
    }
}