package com.ieum.data.datasource.post

import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.GetCommentListResponse
import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.PostCommentRequestBody
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

    suspend fun likePost(id: Int, type: String)

    suspend fun unlikePost(id: Int, type: String)

    suspend fun getCommentList(
        page: Int,
        size: Int,
        postId: Int,
        type: String,
    ): GetCommentListResponse

    suspend fun postComment(postId: Int, type: String, body: PostCommentRequestBody)

    suspend fun deleteComment(postId: Int, type: String, commentId: Int)
}