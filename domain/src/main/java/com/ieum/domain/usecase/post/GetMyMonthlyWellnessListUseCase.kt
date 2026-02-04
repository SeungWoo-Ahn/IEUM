package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.Post
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class GetMyMonthlyWellnessListUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(year: Int, month: Int): Result<List<Post.Wellness>> =
        runCatching {
            postRepository.getMonthlyWellnessList(year = year, month = month)
        }
}