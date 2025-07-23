package com.ieum.presentation.screen.auth.login

sealed class LoginUiState {
    data object Idle : LoginUiState()

    data object Loading : LoginUiState()
}