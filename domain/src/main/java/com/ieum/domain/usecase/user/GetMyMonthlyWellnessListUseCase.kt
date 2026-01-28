package com.ieum.domain.usecase.user

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class GetMyMonthlyWellnessListUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(fromDate: String, toDate: String): Result<List<Post.Wellness>> =
        runCatching {
            userRepository
                .getMyMonthlyPostList(
                    type = PostType.WELLNESS,
                    fromDate = fromDate,
                    toDate = toDate
                )
                .filterIsInstance<Post.Wellness>()
        }
}