package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.repository.PostRepository
import com.ieum.domain.usecase.user.GetMyIdUseCase
import javax.inject.Inject

class GetAllPostListUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val getMyIdUseCase: GetMyIdUseCase,
) {
    suspend operator fun invoke(page: Int, size: Int, diagnosis: Diagnosis?): Result<List<Post>> =
        runCatching {
            val id = getMyIdUseCase().getOrThrow()
            postRepository.getAllPostList(
                page = page,
                size = size,
                diagnosis = diagnosis
            ).map { post ->
                if (post.userInfo?.id == id) {
                    when (post) {
                        is Post.Daily  -> post.copy(isMine = true)
                        is Post.Wellness -> post.copy(isMine = true)
                    }
                } else {
                    post
                }
            }
        }
}