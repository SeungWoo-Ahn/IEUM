package com.ieum.domain.usecase.user

import com.ieum.domain.model.user.MyProfile
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class GetMyProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<MyProfile> = runCatching {
        userRepository.getMyProfile()
    }
}