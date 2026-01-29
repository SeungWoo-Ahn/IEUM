package com.ieum.domain.repository

import androidx.paging.PagingData
import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostCommentRequest
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun postWellness(request: PostWellnessRequest)

    suspend fun patchWellness(id: Int, request: PostWellnessRequest)

    suspend fun deleteWellness(id: Int)

    suspend fun postDaily(request: PostDailyRequest)

    suspend fun patchDaily(id: Int, request: PostDailyRequest)

    suspend fun deleteDaily(id: Int)

    fun getAllPostListFlow(
        diagnosis: Diagnosis?,
        getMyId: suspend () -> Result<Int>
    ): Flow<PagingData<Post>>

    suspend fun likePost(id: Int, type: PostType)

    suspend fun unlikePost(id: Int, type: PostType)

    suspend fun reportPost(id: Int, type: PostType)

    fun getCommentListFlow(
        postId: Int,
        type: PostType,
        getMyId: suspend () -> Result<Int>,
    ): Flow<PagingData<Comment>>

    suspend fun postComment(request: PostCommentRequest)

    suspend fun deleteComment(postId: Int, type: PostType, commentId: Int)

    suspend fun reportComment(postId: Int, type: PostType, commentId: Int)
}