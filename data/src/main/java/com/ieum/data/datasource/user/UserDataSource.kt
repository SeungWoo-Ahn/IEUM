package com.ieum.data.datasource.user

import androidx.paging.PagingData
import com.ieum.data.network.model.post.MyPostDto
import com.ieum.data.network.model.post.OtherPostDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.UserDto
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun register(registerRequestBody: RegisterRequestBody)

    suspend fun getMyProfile(): UserDto

    suspend fun getOthersProfile(id: Int): UserDto

    fun getMyPostListFlow(type: String): Flow<PagingData<MyPostDto>>

    fun getOtherPostListFlow(id: Int, type: String): Flow<PagingData<OtherPostDto>>
}