package com.ieum.data.datasource.user

import com.ieum.data.network.model.post.GetPostListResponse
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.data.network.model.user.MyProfileDto
import com.ieum.data.network.model.user.OthersProfileDto
import com.ieum.data.network.model.user.PatchProfileRequestBody
import com.ieum.data.network.model.user.RegisterRequestBody

interface UserDataSource {
    suspend fun register(requestBody: RegisterRequestBody)

    suspend fun getMyProfile(): MyProfileDto

    suspend fun patchMyProfile(requestBody: PatchProfileRequestBody): MyProfileDto

    suspend fun getOthersProfile(id: Int): OthersProfileDto

    suspend fun getMyPostList(
        page: Int,
        size: Int,
        type: String,
        fromDate: String?,
        toDate: String?,
    ): GetPostListResponse<MyPostDto>

    suspend fun getMyPost(id: Int, type: String): MyPostDto

    suspend fun getOtherPostList(page: Int, size: Int, id: Int): List<OtherPostDto>

    suspend fun withdraw()
}