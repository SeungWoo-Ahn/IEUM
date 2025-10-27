package com.ieum.data.datasource.post

import androidx.paging.PagingData
import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.PostDailyRequestBody
import com.ieum.data.network.model.post.PostDailyResponse
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.data.network.model.post.PostWellnessResponse
import kotlinx.coroutines.flow.Flow

interface PostDataSource {
    suspend fun postWellness(body: PostWellnessRequestBody): PostWellnessResponse

    suspend fun patchWellness(id: Int, body: PostWellnessRequestBody)

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(body: PostDailyRequestBody): PostDailyResponse

    suspend fun patchDaily(id: Int, body: PostDailyRequestBody)

    suspend fun deleteDaily(id: Int)

    fun getAllPostListFlow(diagnosis: String?): Flow<PagingData<AllPostDto>>

    suspend fun getPost(id: Int, type: String): AllPostDto
}