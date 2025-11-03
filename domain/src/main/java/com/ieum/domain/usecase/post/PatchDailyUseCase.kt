package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class PatchDailyUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: Int, request: PostDailyRequest): Result<Unit> = runCatching {
        postRepository.patchDaily(id, request)
    }
}