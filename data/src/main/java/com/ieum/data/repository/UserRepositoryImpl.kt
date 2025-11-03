package com.ieum.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ieum.data.datasource.user.UserDataSource
import com.ieum.data.mapper.asBody
import com.ieum.data.mapper.toDomain
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.User
import com.ieum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getMyPostListFlow(type: PostType): Flow<PagingData<Post>> =
        userDataSource
            .getMyPostListFlow(type.key)
            .map { pagingData ->
                pagingData.map(MyPostDto::toDomain)
            }

    override fun getOthersPostListFlow(id: Int, type: PostType): Flow<PagingData<Post>> =
        userDataSource
            .getOtherPostListFlow(id, type.key)
            .map { pagingData ->
                pagingData.map(OtherPostDto::toDomain)
            }
}
