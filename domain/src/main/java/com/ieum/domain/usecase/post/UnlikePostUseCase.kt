package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class UnlikePostUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(id: Int, type: PostType): Result<Unit> = runCatching {
        postRepository.unlikePost(id, type)
    }
}