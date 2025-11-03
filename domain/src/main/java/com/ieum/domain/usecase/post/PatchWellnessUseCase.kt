package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class PatchWellnessUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(id: Int, request: PostWellnessRequest): Result<Unit> = runCatching {
        postRepository.patchWellness(id, request)
    }
}