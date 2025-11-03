package com.ieum.domain.usecase.user

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class GetOtherPostListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(page: Int, size: Int, id: Int, type: PostType): Result<List<Post>> =
        runCatching {
            userRepository.getOthersPostList(page = page, size = size, id = id, type = type)
        }
}