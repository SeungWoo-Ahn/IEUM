package com.ieum.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ieum.data.datasource.post.PostDataSource
import com.ieum.data.mapper.asBody
import com.ieum.data.mapper.toDomain
import com.ieum.data.network.model.post.AllPostDto
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getAllPostListFlow(diagnosis: Diagnosis?): Flow<PagingData<Post>> =
        postDataSource
            .getAllPostListFlow(diagnosis?.key)
            .map { pagingData ->
                pagingData.map(AllPostDto::toDomain)
            }

    override suspend fun getPost(id: Int, type: PostType): Post =
        postDataSource
            .getPost(id, type.key)
            .toDomain()
}