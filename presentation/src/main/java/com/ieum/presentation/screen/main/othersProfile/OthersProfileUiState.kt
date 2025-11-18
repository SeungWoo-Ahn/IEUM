package com.ieum.presentation.screen.main.othersProfile

import com.ieum.presentation.model.user.OthersProfileUiModel

sealed class OthersProfileUiState {
    data object Loading : OthersProfileUiState()

    data class Success(val profile: OthersProfileUiModel) : OthersProfileUiState()
}