package com.ieum.domain.usecase.user

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class GetMyDailyUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: Int): Result<Post.Daily> = runCatching {
        userRepository.getMyPost(id, PostType.DAILY) as Post.Daily
    }
}