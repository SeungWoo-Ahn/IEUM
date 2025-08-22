package com.ieum.domain.repository

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRecordsRequest
import com.ieum.domain.model.post.PostTreatmentRecordsRequest
import kotlinx.coroutines.flow.StateFlow

interface PostRepository {
    fun getPostList(): StateFlow<List<Post>>

    suspend fun postTreatmentRecords(request: PostTreatmentRecordsRequest)

    suspend fun postDailyRecords(request: PostDailyRecordsRequest)
}