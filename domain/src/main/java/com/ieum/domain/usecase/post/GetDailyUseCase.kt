package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class GetDailyUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: Int): Result<Post.Daily> = runCatching {
        postRepository.getPost(id, PostType.DAILY) as Post.Daily
    }
}