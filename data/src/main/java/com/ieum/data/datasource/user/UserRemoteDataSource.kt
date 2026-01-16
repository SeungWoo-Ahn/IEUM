package com.ieum.data.datasource.user

import com.ieum.data.network.di.IEUMNetwork
import com.ieum.data.network.di.NetworkSource
import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.data.network.model.user.MyProfileDto
import com.ieum.data.network.model.user.OthersProfileDto
import com.ieum.data.network.model.user.PatchProfileRequestBody
import com.ieum.data.network.model.user.RegisterRequestBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    @NetworkSource(IEUMNetwork.Default)
    private val ktorClient: HttpClient,
) : UserDataSource {
    override suspend fun register(requestBody: RegisterRequestBody) {
        ktorClient
            .post("api/v1/users/register") {
                setBody(requestBody)
            }
    }

    override suspend fun getMyProfile(): MyProfileDto =
        ktorClient
            .get("api/v1/users/profile")
            .body<MyProfileDto>()

    override suspend fun patchMyProfile(requestBody: PatchProfileRequestBody): MyProfileDto =
        ktorClient
            .patch("api/v1/users/profile") {
                setBody(requestBody)
            }
            .body<MyProfileDto>()

    override suspend fun getOthersProfile(id: Int): OthersProfileDto =
        ktorClient
            .get("api/v1/users/$id/profile")
            .body<OthersProfileDto>()

    override suspend fun getMyPostList(
        page: Int,
        size: Int,
        type: String,
        fromDate: String?,
        toDate: String?,
    ): List<MyPostDto> =
        ktorClient
            .get("api/v1/users/posts") {
                parameter("page", page)
                parameter("pageSize", size)
                parameter("type", type)
                fromDate?.let { parameter("fromDate", it) }
                toDate?.let { parameter("toDate", it) }
            }
            .body<GetPostListResponse<MyPostDto>>()
            .posts

    override suspend fun getOtherPostList(
        page: Int,
        size: Int,
        id: Int,
    ): List<OtherPostDto> =
        ktorClient
            .get("api/v1/users/${id}/posts") {
                parameter("page", page)
                parameter("pageSize", size)
            }
            .body<GetPostListResponse<OtherPostDto>>()
            .posts
}