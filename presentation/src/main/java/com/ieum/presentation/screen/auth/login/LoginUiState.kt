package com.ieum.presentation.screen.auth.login

sealed interface LoginUiState {
    data object Idle : LoginUiState

    data object Loading : LoginUiState
}