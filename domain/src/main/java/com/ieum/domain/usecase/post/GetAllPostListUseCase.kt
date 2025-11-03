package com.ieum.domain.usecase.post

import com.ieum.domain.model.post.Post
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class GetAllPostListUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(page: Int, size: Int, diagnosis: Diagnosis?): Result<List<Post>> =
        runCatching {
            postRepository.getAllPostList(page = page, size = size, diagnosis = diagnosis)
        }
}