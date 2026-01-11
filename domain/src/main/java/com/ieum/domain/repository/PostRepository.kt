package com.ieum.domain.repository

import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostCommentRequest
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

    suspend fun likePost(id: Int, type: PostType)

    suspend fun unlikePost(id: Int, type: PostType)

    suspend fun getCommentList(page: Int, size: Int, postId: Int, type: PostType): List<Comment>

    suspend fun postComment(request: PostCommentRequest)

    suspend fun deleteComment(postId: Int, type: PostType, commentId: Int)
}