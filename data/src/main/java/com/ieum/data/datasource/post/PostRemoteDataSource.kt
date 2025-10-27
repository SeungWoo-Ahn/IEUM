package com.ieum.data.datasource.post

import androidx.paging.PagingData
import com.ieum.data.network.di.IEUMNetwork
import com.ieum.data.network.di.NetworkSource
import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.PostDailyRequestBody
import com.ieum.data.network.model.post.PostDailyResponse
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.data.network.model.post.PostWellnessResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRemoteDataSource @Inject constructor(
    @NetworkSource(IEUMNetwork.Default)
    private val ktorClient: HttpClient,
) : PostDataSource {
    override suspend fun postWellness(body: PostWellnessRequestBody): PostWellnessResponse =
        ktorClient
            .post("api/v1/posts/wellness") {
                setBody(body)
            }
            .body<PostWellnessResponse>()

    override suspend fun patchWellness(id: Int, body: PostWellnessRequestBody) {
        ktorClient
            .patch("api/v1/posts/wellness/${id}") {
                setBody(body)
            }
    }


    override suspend fun deleteWellness(id: Int) {
        ktorClient.delete("api/v1/posts/wellness/${id}")
    }

    override suspend fun postDaily(body: PostDailyRequestBody): PostDailyResponse =
        ktorClient
            .post("api/v1/posts/daily") {
                setBody(body)
            }
            .body<PostDailyResponse>()

    override suspend fun patchDaily(id: Int, body: PostDailyRequestBody) {
        ktorClient
            .patch("api/v1/posts/daily/${id}") {
                setBody(body)
            }
    }

    override suspend fun deleteDaily(id: Int) {
        ktorClient.delete("api/v1/posts/daily/${id}")
    }

    private suspend fun getAllPostList(diagnosis: String?): GetPostListResponse =
        ktorClient
            .get("api/v1/posts") {
                diagnosis?.let { parameter("diagnosis", it) }
            }
            .body<GetPostListResponse>()

    override fun getAllPostListFlow(diagnosis: String?): Flow<PagingData<AllPostDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(id: Int, type: String): AllPostDto =
        ktorClient
            .get("api/v1/posts/${type}/${id}")
            .body<AllPostDto>()
}