package com.ieum.data.repository

import com.ieum.data.datasource.user.UserDataSource
import com.ieum.data.mapper.asBody
import com.ieum.data.mapper.toDomain
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.User
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun register(registerRequest: RegisterRequest) =
        userDataSource
            .register(registerRequest.asBody())

    override suspend fun getMyProfile(): User =
        userDataSource
            .getMyProfile()
            .toDomain()

    override suspend fun getOthersProfile(id: Int): User =
        userDataSource
            .getOthersProfile(id)
            .toDomain()
}
