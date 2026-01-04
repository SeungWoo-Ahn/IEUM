package com.ieum.domain.usecase.user

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import javax.inject.Inject

class GetMyWellnessListByMonthUseCase @Inject constructor(
    private val getMyPostListUseCase: GetMyPostListUseCase,
) {
    suspend operator fun invoke(fromDate: String, toDate: String): Result<List<Post.Wellness>> =
        runCatching {
            getMyPostListUseCase(
                page = 1,
                size = 100,
                type = PostType.WELLNESS,
                fromDate = fromDate,
                toDate = toDate
            )
                .getOrThrow()
                .filterIsInstance<Post.Wellness>()
        }
}