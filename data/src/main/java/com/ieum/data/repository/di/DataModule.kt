package com.ieum.data.repository.di

import com.ieum.data.repository.AddressRepositoryImpl
import com.ieum.data.repository.AuthRepositoryImpl
import com.ieum.data.repository.PostRepositoryImpl
import com.ieum.data.repository.PreferenceRepositoryImpl
import com.ieum.data.repository.UserRepositoryImpl
import com.ieum.domain.repository.AddressRepository
import com.ieum.domain.repository.AuthRepository
import com.ieum.domain.repository.PostRepository
import com.ieum.domain.repository.PreferenceRepository
import com.ieum.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindsAddressRepository(addressRepository: AddressRepositoryImpl): AddressRepository

    @Binds
    fun bindsAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindsUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    fun bindsPostRepository(postRepository: PostRepositoryImpl): PostRepository

    @Binds
    fun bindsPreferenceRepository(preferenceRepository: PreferenceRepositoryImpl): PreferenceRepository
}