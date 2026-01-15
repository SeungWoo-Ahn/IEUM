package com.ieum.domain.usecase.user

import androidx.paging.PagingData
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyPostListFlowUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getMyIdUseCase: GetMyIdUseCase,
) {
    operator fun invoke(type: PostType): Flow<PagingData<Post>> =
        flow {
            val userId = getMyIdUseCase().getOrThrow()
            emit(userId)
        }
            .flatMapLatest { userId ->
                userRepository.getMyPostListFlow(userId, type)
            }
}