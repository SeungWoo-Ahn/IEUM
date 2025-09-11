package com.ieum.domain.repository

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun postWellness(postWellnessRequest: PostWellnessRequest): Int

    suspend fun patchWellness(id: Int, postWellnessRequest: PostWellnessRequest): Int

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(postDailyRequest: PostDailyRequest): Int

    suspend fun patchDaily(id: Int, postDailyRequest: PostDailyRequest): Int

    suspend fun deleteDaily(id: Int)

    fun getAllPostListFlow(): Flow<List<Post>>

    suspend fun getPost(id: Int, type: PostType): Post
}