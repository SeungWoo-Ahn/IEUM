package com.ieum.domain.usecase.user

import androidx.paging.PagingData
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyPostListFlowUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(type: PostType): Flow<PagingData<Post>> =
        userRepository.getMyPostListFlow(type)
}