package com.ieum.domain.repository

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.user.Profile
import com.ieum.domain.model.user.RegisterRequest

interface UserRepository {
    suspend fun register(registerRequest: RegisterRequest)

    suspend fun getMyProfile(): Profile

    suspend fun getOthersProfile(id: Int): Profile

    suspend fun getMyPostList(page: Int, size: Int, type: PostType): List<Post>

    suspend fun getOthersPostList(page: Int, size: Int, id: Int, type: PostType): List<Post>
}