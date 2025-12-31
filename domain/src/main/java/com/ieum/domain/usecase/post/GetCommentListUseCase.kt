package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.PostRepository
import com.ieum.domain.usecase.user.GetMyIdUseCase
import javax.inject.Inject

class GetCommentListUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val getMyIdUseCase: GetMyIdUseCase,
) {
    suspend operator fun invoke(
        page: Int,
        size: Int,
        postId: Int,
        type: PostType
    ): Result<List<Comment>> = runCatching {
        val id = getMyIdUseCase().getOrThrow()
        postRepository.getCommentList(
            page = page,
            size = size,
            postId = postId,
            type = type,
        ).map { comment ->
            if (comment.userId == id) {
                comment.copy(isMine = true)
            } else {
                comment
            }
        }
    }
}