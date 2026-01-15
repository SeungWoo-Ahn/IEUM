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

    suspend fun getMyPostList(
        page: Int,
        size: Int,
        type: PostType,
        fromDate: String?,
        toDate: String?,
    ): List<Post>

    fun getMyPostListFlow(userId: Int, type: PostType): Flow<PagingData<Post>>

    suspend fun getOthersPostList(page: Int, size: Int, id: Int): List<Post>
}