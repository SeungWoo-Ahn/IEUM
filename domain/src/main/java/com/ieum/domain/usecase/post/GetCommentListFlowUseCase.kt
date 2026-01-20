package com.ieum.domain.usecase.post

import androidx.paging.PagingData
import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.PostRepository
import com.ieum.domain.usecase.user.GetMyIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentListFlowUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val getMyIdUseCase: GetMyIdUseCase,
) {
    operator fun invoke(postId: Int, type: PostType): Flow<PagingData<Comment>> =
        postRepository.getCommentListFlow(
            postId = postId,
            type = type,
            getMyId = getMyIdUseCase::invoke,
        )
}