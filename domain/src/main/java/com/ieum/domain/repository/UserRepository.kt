package com.ieum.domain.repository

import androidx.paging.PagingData
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.user.MyProfile
import com.ieum.domain.model.user.OthersProfile
import com.ieum.domain.model.user.PatchProfileRequest
import com.ieum.domain.model.user.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun register(request: RegisterRequest)

    suspend fun getMyProfile(): MyProfile

    suspend fun patchMyProfile(request: PatchProfileRequest): MyProfile

    suspend fun getOthersProfile(id: Int): OthersProfile
    suspend fun getMyPost(id: Int, type: PostType): Post

    fun getMyPostListFlow(type: PostType): Flow<PagingData<Post>>

    fun getOthersPostListFlow(id: Int): Flow<PagingData<Post>>

    suspend fun withdraw()
}