package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class PostWellnessUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(request: PostWellnessRequest): Result<Unit> = runCatching {
        postRepository.postWellness(request)
    }
}