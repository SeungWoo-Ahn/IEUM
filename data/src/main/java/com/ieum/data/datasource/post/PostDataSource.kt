package com.ieum.data.datasource.post

import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.CommentDto
import com.ieum.data.network.model.post.MonthlyWellnessDto
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

    suspend fun patchWellness(
        id: Int,
        body: PostWellnessRequestBody,
        fileList: List<File>
    ): PostWellnessResponse

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(
        body: PostDailyRequestBody,
        fileList: List<File>,
    ): PostDailyResponse

    suspend fun patchDaily(
        id: Int,
        body: PostDailyRequestBody,
        fileList: List<File>
    ): PostDailyResponse

    suspend fun deleteDaily(id: Int)

    suspend fun getAllPostList(
        page: Int,
        size: Int,
        diagnosis: String?
    ): List<AllPostDto>

    suspend fun getMonthlyWellnessList(
        year: Int,
        month: Int,
    ): List<MonthlyWellnessDto>

    suspend fun likePost(id: Int, type: String)

    suspend fun unlikePost(id: Int, type: String)

    suspend fun getCommentList(
        page: Int,
        size: Int,
        postId: Int,
        type: String,
    ): List<CommentDto>

    suspend fun postComment(postId: Int, type: String, body: PostCommentRequestBody): CommentDto

    suspend fun deleteComment(postId: Int, type: String, commentId: Int)
}