package com.ieum.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.ieum.data.datastore.TokenSerializer
import com.ieum.domain.model.auth.Token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    @Provides
    @Singleton
    fun providesTokenDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Token?> =
        DataStoreFactory.create(
            serializer = TokenSerializer,
            produceFile = { context.dataStoreFile("token.pb") }
        )
}