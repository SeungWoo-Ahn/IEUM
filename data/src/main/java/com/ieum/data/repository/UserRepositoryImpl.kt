package com.ieum.data.repository

import com.ieum.data.datasource.user.UserDataSource
import com.ieum.data.mapper.asBody
import com.ieum.data.mapper.toDomain
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.user.PatchProfileRequest
import com.ieum.domain.model.user.Profile
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun register(request: RegisterRequest) =
        userDataSource
            .register(request.asBody())

    override suspend fun getMyProfile(): Profile =
        userDataSource
            .getMyProfile()
            .toDomain()

    override suspend fun patchMyProfile(request: PatchProfileRequest) =
        userDataSource
            .patchMyProfile(request.asBody())

    override suspend fun getOthersProfile(id: Int): Profile =
        userDataSource
            .getOthersProfile(id)
            .toDomain()

    override suspend fun getMyPostList(page: Int, size: Int, type: PostType): List<Post> =
        userDataSource
            .getMyPostList(page = page, size = size, type = type.key)
            .posts
            .map(MyPostDto::toDomain)

    override suspend fun getOthersPostList(
        page: Int,
        size: Int,
        id: Int,
        type: PostType
    ): List<Post> =
        userDataSource
            .getOtherPostList(page = page, size = size, id = id, type = type.key)
            .posts
            .map(OtherPostDto::toDomain)
}
