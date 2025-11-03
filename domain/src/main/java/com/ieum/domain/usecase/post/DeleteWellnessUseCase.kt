package com.ieum.domain.usecase.post

import com.ieum.domain.repository.PostRepository
import javax.inject.Inject

class DeleteWellnessUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(id: Int): Result<Unit> = runCatching {
        postRepository.deleteWellness(id)
    }
}