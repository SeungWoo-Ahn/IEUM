package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class GetCommentListUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(
        page: Int,
        size: Int,
        postId: Int,
        type: PostType
    ): Result<List<Comment>> = runCatching {
        postRepository.getCommentList(
            page = page,
            size = size,
            postId = postId,
            type = type,
        )
    }
}