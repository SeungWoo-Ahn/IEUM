package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.PostCommentRequest
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(request: PostCommentRequest): Result<Unit> = runCatching {
        postRepository.postComment(request)
    }
}