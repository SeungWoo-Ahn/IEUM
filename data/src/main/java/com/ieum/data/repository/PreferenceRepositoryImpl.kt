package com.ieum.data.repository

import androidx.datastore.core.DataStore
import com.ieum.data.repository.di.ApplicationScope
import com.ieum.domain.model.auth.Token
import com.ieum.domain.repository.PreferenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val tokenDataStore: DataStore<Token?>,
    @ApplicationScope private val scope: CoroutineScope,
) : PreferenceRepository {
    override val tokenFlow: Flow<Token?> get() = tokenDataStore.data

    private val cachedToken = MutableStateFlow<Token?>(null)
    private val cachedMyId = MutableStateFlow<Int?>(null)

    private var pendingToken: Token? = null

    init {
        scope.launch {
            tokenDataStore.data.collect { token ->
                cachedToken.update { token }
            }
        }
    }

    override fun getCachedToken(): Token? {
        return cachedToken.value
    }

    override suspend fun saveToken(token: Token) {
        tokenDataStore.updateData { token }
    }

    override fun setPendingToken(token: Token) {
        pendingToken = token
    }

    override suspend fun savePendingToken() {
        pendingToken?.let { token ->
            cachedToken.update { token }
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