package com.ieum.domain.repository

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.User

interface UserRepository {
    suspend fun register(registerRequest: RegisterRequest)

    suspend fun getMyProfile(): User

    suspend fun getOthersProfile(id: Int): User

    suspend fun getMyPostList(page: Int, size: Int, type: PostType): List<Post>

    suspend fun getOthersPostList(page: Int, size: Int, id: Int, type: PostType): List<Post>
}