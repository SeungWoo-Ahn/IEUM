package com.ieum.domain.repository

import androidx.paging.PagingData
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun postWellness(request: PostWellnessRequest): Int

    suspend fun patchWellness(id: Int, request: PostWellnessRequest): Int

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(request: PostDailyRequest): Int

    suspend fun patchDaily(id: Int, request: PostDailyRequest): Int

    suspend fun deleteDaily(id: Int)

    fun getAllPostListFlow(diagnosis: Diagnosis?): Flow<PagingData<Post>>

    suspend fun getPost(id: Int, type: PostType): Post
}