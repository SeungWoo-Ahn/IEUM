package com.ieum.data.datasource.user

import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.UserDto

interface UserDataSource {
    suspend fun register(registerRequestBody: RegisterRequestBody)

    suspend fun getMyProfile(): UserDto

    suspend fun getOthersProfile(id: Int): UserDto

    suspend fun getMyPostList(page: Int, size: Int, type: String): GetPostListResponse<MyPostDto>

    suspend fun getOtherPostList(
        page: Int,
        size: Int,
        id: Int,
        type: String
    ): GetPostListResponse<OtherPostDto>
}