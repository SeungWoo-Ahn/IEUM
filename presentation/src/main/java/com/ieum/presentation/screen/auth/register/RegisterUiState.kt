package com.ieum.presentation.screen.auth.register

sealed class RegisterUiState {
    data object Idle : RegisterUiState()

    data object Loading : RegisterUiState()
}