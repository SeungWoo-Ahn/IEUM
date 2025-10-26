package com.ieum.domain.repository

import androidx.paging.PagingData
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun register(registerRequest: RegisterRequest)

    suspend fun getMyProfile(): User

    suspend fun getOthersProfile(id: Int): User

    fun getMyPostListFlow(type: PostType): Flow<PagingData<Post>>

    fun getOthersPostListFlow(type: PostType): Flow<PagingData<Post>>
}