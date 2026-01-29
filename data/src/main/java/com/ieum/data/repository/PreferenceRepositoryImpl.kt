package com.ieum.data.repository

import androidx.datastore.core.DataStore
import com.ieum.domain.model.auth.Token
import com.ieum.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val tokenDataStore: DataStore<Token?>,
) : PreferenceRepository {
    override val tokenFlow: Flow<Token?> get() = tokenDataStore.data

    private val cachedMyId = MutableStateFlow<Int?>(null)
    private var pendingToken: Token? = null


    override suspend fun saveToken(token: Token) {
        tokenDataStore.updateData { token }
    }

    override fun setPendingToken(token: Token) {
        pendingToken = token
    }

    override suspend fun savePendingToken() {
        pendingToken?.let { token ->
            saveToken(token)
        }
    }

    override fun getMyId(): Int? {
        return cachedMyId.value
    }

    override fun setMyId(id: Int) {
        cachedMyId.update { id }
    }

    override suspend fun clear() {
        tokenDataStore.updateData { null }
        pendingToken = null
        cachedMyId.update { null }
    }
}