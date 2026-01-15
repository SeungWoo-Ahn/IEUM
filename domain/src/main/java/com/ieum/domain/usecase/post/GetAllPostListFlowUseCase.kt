package com.ieum.domain.usecase.post

import androidx.paging.PagingData
import androidx.paging.map
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.repository.PostRepository
import com.ieum.domain.usecase.user.GetMyIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPostListFlowUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val getMyIdUseCase: GetMyIdUseCase,
) {
    operator fun invoke(diagnosis: Diagnosis?): Flow<PagingData<Post>> =
        flow {
            val userId = getMyIdUseCase().getOrThrow()
            emit(userId)
        }
            .flatMapLatest { userId ->
                postRepository
                    .getAllPostListFlow(diagnosis)
                    .map { pagingData ->
                        pagingData.map {
                            when (it) {
                                is Post.Wellness -> it.copy(isMine = it.userInfo?.id == userId)
                                is Post.Daily -> it.copy(isMine = it.userInfo?.id == userId)
                            }
                        }
                    }
            }
}