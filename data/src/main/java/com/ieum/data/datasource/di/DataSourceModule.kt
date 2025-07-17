package com.ieum.data.datasource.di

import com.ieum.data.datasource.address.AddressDataCache
import com.ieum.data.datasource.address.AddressDataSource
import com.ieum.data.datasource.address.AddressLocalDataCache
import com.ieum.data.datasource.address.AddressRemoteDataSource
import com.ieum.data.datasource.auth.AuthDataSource
import com.ieum.data.datasource.auth.AuthRemoteDataSource
import com.ieum.data.datasource.user.UserDataSource
import com.ieum.data.datasource.user.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun bindsAddressDataCache(addressDataCache: AddressLocalDataCache): AddressDataCache

    @Binds
    fun bindsAddressDataSource(addressDataSource: AddressRemoteDataSource): AddressDataSource

    @Binds
    fun bindsAuthDataSource(authDataSource: AuthRemoteDataSource): AuthDataSource

    @Binds
    fun bindsUserDataSource(userDataSource: UserRemoteDataSource): UserDataSource
}