package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class PostDailyUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(request: PostDailyRequest): Result<Int> = runCatching {
        postRepository.postDaily(request)
    }
}