package com.ieum.domain.repository

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.user.OthersProfile
import com.ieum.domain.model.user.PatchProfileRequest
import com.ieum.domain.model.user.Profile
import com.ieum.domain.model.user.RegisterRequest

interface UserRepository {
    suspend fun register(request: RegisterRequest)

    suspend fun getMyProfile(): Profile

    suspend fun patchMyProfile(request: PatchProfileRequest)

    suspend fun getOthersProfile(id: Int): OthersProfile

    suspend fun getMyPostList(page: Int, size: Int, type: PostType): List<Post>

    suspend fun getOthersPostList(page: Int, size: Int, id: Int): List<Post>
}