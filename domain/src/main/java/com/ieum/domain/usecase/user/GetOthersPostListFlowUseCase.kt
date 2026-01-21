package com.ieum.domain.usecase.user

import androidx.paging.PagingData
import com.ieum.domain.model.post.Post
import com.ieum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOthersPostListFlowUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(id: Int): Flow<PagingData<Post>> =
        userRepository.getOthersPostListFlow(id)
}