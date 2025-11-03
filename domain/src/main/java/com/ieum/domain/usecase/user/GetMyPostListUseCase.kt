package com.ieum.domain.usecase.user

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class GetMyPostListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(page: Int, size: Int, type: PostType): Result<List<Post>> =
        runCatching {
            userRepository.getMyPostList(page = page, size = size, type = type)
        }
}