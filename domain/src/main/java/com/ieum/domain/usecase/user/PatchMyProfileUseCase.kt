package com.ieum.domain.usecase.user

import com.ieum.domain.model.user.MyProfile
import com.ieum.domain.model.user.PatchProfileRequest
import com.ieum.domain.repository.UserRepository
import javax.inject.Inject

class PatchMyProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(request: PatchProfileRequest): Result<MyProfile> = runCatching {
        userRepository.patchMyProfile(request)
    }
}