package com.ieum.data.repository

import com.ieum.data.datasource.post.PostDataSource
import com.ieum.data.mapper.asBody
import com.ieum.data.mapper.toDomain
import com.ieum.data.network.model.post.AllPostDto
import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val postDataSource: PostDataSource
) : PostRepository {
    override suspend fun postWellness(request: PostWellnessRequest): Int =
        postDataSource
            .postWellness(
                body = request.asBody(),
                fileList = request.imageList.map(ImageSource.Local::file)
            )
            .id

    override suspend fun patchWellness(id: Int, request: PostWellnessRequest) {
        postDataSource.patchWellness(id, request.asBody())
    }

    override suspend fun deleteWellness(id: Int) {
        postDataSource.deleteWellness(id)
    }

    override suspend fun postDaily(request: PostDailyRequest): Int =
        postDataSource
            .postDaily(
                body = request.asBody(),
                fileList = request.imageList.map(ImageSource.Local::file)
            )
            .id

    override suspend fun patchDaily(id: Int, request: PostDailyRequest) {
        postDataSource.patchDaily(id, request.asBody())
    }

    override suspend fun deleteDaily(id: Int) {
        postDataSource.deleteWellness(id)
    }

    override suspend fun getAllPostList(page: Int, size: Int, diagnosis: Diagnosis?): List<Post> =
        postDataSource
            .getAllPostList(page = page, size = size, diagnosis = diagnosis?.key)
            .posts
            .map(AllPostDto::toDomain)

    override suspend fun getPost(id: Int, type: PostType): Post =
        postDataSource
            .getPost(id, type.key)
            .toDomain()
}