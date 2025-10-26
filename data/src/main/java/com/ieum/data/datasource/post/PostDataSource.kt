package com.ieum.data.datasource.post

import androidx.paging.PagingData
import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.PostDailyRequestBody
import com.ieum.data.network.model.post.PostDailyResponse
import com.ieum.data.network.model.post.PostDto
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.data.network.model.post.PostWellnessResponse
import kotlinx.coroutines.flow.Flow

interface PostDataSource {
    suspend fun postWellness(body: PostWellnessRequestBody): PostWellnessResponse

    suspend fun patchWellness(id: Int, body: PostWellnessRequestBody): PostWellnessResponse

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(body: PostDailyRequestBody): PostDailyResponse

    suspend fun patchDaily(id: Int, body: PostDailyRequestBody): PostDailyResponse

    suspend fun deleteDaily(id: Int)

    suspend fun getAllPostList(diagnosis: String?): GetPostListResponse

    fun getAllPostListFlow(diagnosis: String?): Flow<PagingData<PostDto>>

    suspend fun getPost(id: Int, type: String): PostDto
}