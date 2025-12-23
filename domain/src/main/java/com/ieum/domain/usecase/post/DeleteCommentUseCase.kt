package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(
        postId: Int,
        type: PostType,
        commentId: Int
    ): Result<Unit> = runCatching {
        postRepository.deleteComment(
            postId = postId,
            type = type,
            commentId = commentId,
        )
    }
}