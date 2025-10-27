package com.ieum.data.repository

import androidx.paging.PagingData
import com.ieum.data.datasource.post.PostDataSource
import com.ieum.data.mapper.asBody
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val postDataSource: PostDataSource
) : PostRepository {
    override suspend fun postWellness(request: PostWellnessRequest): Int =
        postDataSource
            .postWellness(request.asBody())
            .id

    override suspend fun patchWellness(id: Int, request: PostWellnessRequest) {
        postDataSource.patchWellness(id, request.asBody())
    }

    override suspend fun deleteWellness(id: Int) {
        postDataSource.deleteWellness(id)
    }

    override suspend fun postDaily(request: PostDailyRequest): Int =
        postDataSource
            .postDaily(request.asBody())
            .id

    override suspend fun patchDaily(id: Int, request: PostDailyRequest) {
        postDataSource.patchDaily(id, request.asBody())
    }

    override suspend fun deleteDaily(id: Int) {
        postDataSource.deleteWellness(id)
    }

    override fun getAllPostListFlow(diagnosis: List<Diagnosis>): Flow<PagingData<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(id: Int, type: PostType): Post {
        TODO("Not yet implemented")
    }
}