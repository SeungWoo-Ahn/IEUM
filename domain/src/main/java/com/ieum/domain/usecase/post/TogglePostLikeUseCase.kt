package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class TogglePostLikeUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(
        id: Int,
        type: PostType,
        isLiked: Boolean
    ): Result<Unit> = runCatching {
        if (isLiked) {
            postRepository.unlikePost(id, type)
        } else {
            postRepository.likePost(id, type)
        }
    }
}