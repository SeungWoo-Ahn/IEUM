package com.ieum.domain.usecase.user

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class GetMyWellnessUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: Int): Result<Post.Wellness> = runCatching {
        userRepository.getMyPost(id, PostType.WELLNESS) as Post.Wellness
    }
}