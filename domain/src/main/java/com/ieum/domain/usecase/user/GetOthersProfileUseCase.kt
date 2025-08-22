package com.ieum.domain.usecase.user

import com.ieum.domain.model.user.User
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class GetOthersProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: Int): Result<User> = runCatching {
        userRepository.getOthersProfile(id)
    }
}