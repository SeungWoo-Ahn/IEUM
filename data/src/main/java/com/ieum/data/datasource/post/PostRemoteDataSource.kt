package com.ieum.data.datasource.post

import com.ieum.data.network.di.IEUMNetwork
import com.ieum.data.network.di.NetworkSource
import com.ieum.data.network.model.post.AllPostDto
import com.ieum.data.network.model.post.CommentDto
import com.ieum.data.network.model.post.GetCommentListResponse
import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.PostCommentRequestBody
import com.ieum.data.network.model.post.PostDailyRequestBody
import com.ieum.data.network.model.post.PostDailyResponse
import com.ieum.data.network.model.post.PostWellnessRequestBody
import com.ieum.data.network.model.post.PostWellnessResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRemoteDataSource @Inject constructor(
    @NetworkSource(IEUMNetwork.Default)
    private val ktorClient: HttpClient,
) : PostDataSource {
    private fun getMimeType(fileName: String): String =
        when (fileName.substringAfterLast('.')) {
            "jpeg" -> "image/jpeg"
            "webp" -> "image/webp"
            else -> "image/*"
        }

    private suspend inline fun <reified T> submitFormWithImages(
        httpMethod: HttpMethod,
        url: String,
        body: T,
        fileList: List<File>,
    ): HttpResponse =
        ktorClient
            .submitFormWithBinaryData(
                url = url,
                formData = formData {
                    append(
                        key = "data",
                        value = Json.encodeToString(body),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        }
                    )
                    fileList.forEach { file ->
                        val contentType = getMimeType(file.name)
                        val contentDisposition = "filename=\"${file.name}\""
                        append(
                            key = "images",
                            value = file.readBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, contentType)
                                append(HttpHeaders.ContentDisposition, contentDisposition)
                            }
                        )
                    }
                }
            ) {
                method = httpMethod
            }

    override suspend fun postWellness(
        body: PostWellnessRequestBody,
        fileList: List<File>
    ): PostWellnessResponse =
        submitFormWithImages(
            httpMethod = HttpMethod.Post,
            url = "api/v1/posts/wellness",
            body = body,
            fileList = fileList,
        )
            .body<PostWellnessResponse>()

    override suspend fun patchWellness(
        id: Int,
        body: PostWellnessRequestBody,
        fileList: List<File>
    ): PostWellnessResponse =
        submitFormWithImages(
            httpMethod = HttpMethod.Patch,
            url = "api/v1/posts/wellness/${id}",
            body = body,
            fileList = fileList,
        )
            .body<PostWellnessResponse>()

    override suspend fun deleteWellness(id: Int) {
        ktorClient.delete("api/v1/posts/wellness/${id}")
    }

    override suspend fun postDaily(
        body: PostDailyRequestBody,
        fileList: List<File>
    ): PostDailyResponse =
        submitFormWithImages(
            httpMethod = HttpMethod.Post,
            url = "api/v1/posts/daily",
            body = body,
            fileList = fileList,
        )
            .body<PostDailyResponse>()

    override suspend fun patchDaily(
        id: Int,
        body: PostDailyRequestBody,
        fileList: List<File>,
    ): PostDailyResponse =
        submitFormWithImages(
            httpMethod = HttpMethod.Patch,
            url = "api/v1/posts/daily/${id}",
            body = body,
            fileList = fileList,
        )
            .body<PostDailyResponse>()

    override suspend fun deleteDaily(id: Int) {
        ktorClient.delete("api/v1/posts/daily/${id}")
    }

    override suspend fun getAllPostList(
        page: Int,
        size: Int,
        diagnosis: String?,
    ): List<AllPostDto> =
        ktorClient
            .get("api/v1/posts") {
                parameter("page", page)
                parameter("pageSize", size)
                diagnosis?.let { parameter("diagnosis", it) }
            }
            .body<GetPostListResponse<AllPostDto>>()
            .posts

    override suspend fun getPost(id: Int, type: String): AllPostDto =
        ktorClient
            .get("api/v1/posts/${type}/${id}")
            .body<AllPostDto>()

    override suspend fun likePost(id: Int, type: String) {
        ktorClient.post("api/v1/posts/${type}/${id}/like")
    }

    override suspend fun unlikePost(id: Int, type: String) {
        ktorClient.delete("api/v1/posts/${type}/${id}/like")
    }

    override suspend fun getCommentList(
        page: Int,
        size: Int,
        postId: Int,
        type: String
    ): List<CommentDto> =
        ktorClient
            .get("api/v1/posts/${type}/${postId}/comments")
            .body<GetCommentListResponse>()
            .comments

    override suspend fun postComment(
        postId: Int,
        type: String,
        body: PostCommentRequestBody
    ) {
        ktorClient
            .post("api/v1/posts/${type}/${postId}/comments") {
                setBody(body)
            }
    }

    override suspend fun deleteComment(
        postId: Int,
        type: String,
        commentId: Int
    ) {
        ktorClient.delete("api/v1/posts/${type}/${postId}/comments/${commentId}")
    }
}