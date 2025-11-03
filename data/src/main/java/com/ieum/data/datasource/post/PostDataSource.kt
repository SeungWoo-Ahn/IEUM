package com.ieum.data.datasource.post

import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.PostDailyRequestBody
import com.ieum.data.network.model.post.PostDailyResponse
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.data.network.model.post.PostWellnessResponse

interface PostDataSource {
    suspend fun postWellness(body: PostWellnessRequestBody): PostWellnessResponse

    suspend fun patchWellness(id: Int, body: PostWellnessRequestBody)

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(body: PostDailyRequestBody): PostDailyResponse

    suspend fun patchDaily(id: Int, body: PostDailyRequestBody)

    suspend fun deleteDaily(id: Int)

    suspend fun getAllPostList(page: Int, size: Int, diagnosis: String?): GetPostListResponse<AllPostDto>

    suspend fun getPost(id: Int, type: String): AllPostDto
}