package com.ieum.data.datasource.user

import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.data.network.model.user.MyProfileDto
import com.ieum.data.network.model.user.OtherProfileDto
import com.ieum.data.network.model.user.RegisterRequestBody

interface UserDataSource {
    suspend fun register(registerRequestBody: RegisterRequestBody)

    suspend fun getMyProfile(): MyProfileDto

    suspend fun getOthersProfile(id: Int): OtherProfileDto

    suspend fun getMyPostList(page: Int, size: Int, type: String): GetPostListResponse<MyPostDto>

    suspend fun getOtherPostList(
        page: Int,
        size: Int,
        id: Int,
        type: String
    ): GetPostListResponse<OtherPostDto>
}