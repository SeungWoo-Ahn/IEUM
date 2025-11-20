package com.ieum.presentation.screen.main.home.myProfile

import com.ieum.domain.model.user.MyProfile

sealed class MyProfileUiState {
    data object Loading : MyProfileUiState()

    data class Success(val profile: MyProfile) : MyProfileUiState()
}