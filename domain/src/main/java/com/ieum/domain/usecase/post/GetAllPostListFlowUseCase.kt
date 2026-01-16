package com.ieum.domain.usecase.post

import androidx.paging.PagingData
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.repository.PostRepository
import com.ieum.domain.usecase.user.GetMyIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPostListFlowUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val getMyIdUseCase: GetMyIdUseCase,
) {
    operator fun invoke(diagnosis: Diagnosis?): Flow<PagingData<Post>> =
        postRepository.getAllPostListFlow(
            diagnosis = diagnosis,
            getMyId = { getMyIdUseCase() }
        )
}