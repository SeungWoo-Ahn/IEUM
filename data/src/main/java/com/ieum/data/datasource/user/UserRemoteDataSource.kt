package com.ieum.data.datasource.user

import androidx.paging.PagingData
import com.ieum.data.network.di.IEUMNetwork
import com.ieum.data.network.di.NetworkSource
import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    @NetworkSource(IEUMNetwork.Default)
    private val ktorClient: HttpClient,
) : UserDataSource {
    override suspend fun register(registerRequestBody: RegisterRequestBody) {
        ktorClient
            .post("api/v1/users/register") {
                setBody(registerRequestBody)
            }
    }

    override suspend fun getMyProfile(): UserDto =
        ktorClient
            .get("api/v1/users/profile")
            .body<UserDto>()

    override suspend fun getOthersProfile(id: Int): UserDto =
        ktorClient
            .get("api/v1/users/$id/profile")
            .body<UserDto>()

    private suspend fun getMyPostList(type: String): GetPostListResponse<MyPostDto> =
        ktorClient
            .get("api/v1/users/posts") {
                parameter("type", type)
            }
            .body<GetPostListResponse<MyPostDto>>()

    override fun getMyPostListFlow(type: String): Flow<PagingData<MyPostDto>> {
        TODO("Not yet implemented")
    }

    private suspend fun getOtherPostList(id: Int, type: String): GetPostListResponse<OtherPostDto> =
        ktorClient
            .get("api/v1/users/${id}/posts") {
                parameter("type", type)
            }
            .body<GetPostListResponse<OtherPostDto>>()

    override fun getOtherPostListFlow(id: Int, type: String): Flow<PagingData<OtherPostDto>> {
        TODO("Not yet implemented")
    }
}