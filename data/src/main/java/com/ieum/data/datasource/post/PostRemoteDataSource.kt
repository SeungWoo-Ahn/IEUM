package com.ieum.data.datasource.post

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
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
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

    override suspend fun postWellness(
        body: PostWellnessRequestBody,
        fileList: List<File>
    ): PostWellnessResponse =
        ktorClient
            .submitFormWithBinaryData(
                url = "api/v1/posts/wellness",
                formData = formData {
                    append(
                        key = "body", // TODO: 키 변경
                        value = Json.encodeToString(body),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        }
                    )
                    fileList.forEach { file ->
                        val contentType = getMimeType(file.name)
                        val contentDisposition = "filename=\"${file.name}\""
                        append(
                            key = "files", // TODO: 키 변경
                            value = file.readBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, contentType)
                                append(HttpHeaders.ContentDisposition, contentDisposition)
                            }
                        )
                    }
                }
            )
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

    override suspend fun postDaily(
        body: PostDailyRequestBody,
        fileList: List<File>
    ): PostDailyResponse =
        ktorClient
            .submitFormWithBinaryData(
                url = "api/v1/posts/daily",
                formData = formData {
                    append(
                        key = "body", // TODO: 키 변경
                        value = Json.encodeToString(body),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        }
                    )
                    fileList.forEach { file ->
                        val contentType = getMimeType(file.name)
                        val contentDisposition = "filename=\"${file.name}\""
                        append(
                            key = "files", // TODO: 키 변경
                            value = file.readBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, contentType)
                                append(HttpHeaders.ContentDisposition, contentDisposition)
                            }
                        )
                    }
                }
            )
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

    override suspend fun getAllPostList(
        page: Int,
        size: Int,
        diagnosis: String?,
    ): GetPostListResponse<AllPostDto> =
        ktorClient
            .get("api/v1/posts") {
                parameter("page", page)
                parameter("pageSize", size)
                diagnosis?.let { parameter("diagnosis", it) }
            }
            .body<GetPostListResponse<AllPostDto>>()

    override suspend fun getPost(id: Int, type: String): AllPostDto =
        ktorClient
            .get("api/v1/posts/${type}/${id}")
            .body<AllPostDto>()
}