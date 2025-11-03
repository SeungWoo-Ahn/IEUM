package com.ieum.domain.repository

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis

interface PostRepository {
    suspend fun postWellness(request: PostWellnessRequest): Int

    suspend fun patchWellness(id: Int, request: PostWellnessRequest)

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(request: PostDailyRequest): Int

    suspend fun patchDaily(id: Int, request: PostDailyRequest)

    suspend fun deleteDaily(id: Int)

    suspend fun getAllPostList(page: Int, size: Int, diagnosis: Diagnosis?): List<Post>

    suspend fun getPost(id: Int, type: PostType): Post
}