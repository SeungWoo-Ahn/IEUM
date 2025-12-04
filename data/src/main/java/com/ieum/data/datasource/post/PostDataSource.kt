package com.ieum.data.datasource.post

import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.PostDailyRequestBody
import com.ieum.data.network.model.post.PostDailyResponse
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.data.network.model.post.PostWellnessResponse
import java.io.File

interface PostDataSource {
    suspend fun postWellness(
        body: PostWellnessRequestBody,
        fileList: List<File>,
    ): PostWellnessResponse

    suspend fun patchWellness(id: Int, body: PostWellnessRequestBody, fileList: List<File>)

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(
        body: PostDailyRequestBody,
        fileList: List<File>,
    ): PostDailyResponse

    suspend fun patchDaily(id: Int, body: PostDailyRequestBody, fileList: List<File>)

    suspend fun deleteDaily(id: Int)

    suspend fun getAllPostList(
        page: Int,
        size: Int,
        diagnosis: String?
    ): GetPostListResponse<AllPostDto>

    suspend fun getPost(id: Int, type: String): AllPostDto
}