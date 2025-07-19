package com.ieum.presentation.screen.auth.register

sealed interface RegisterUiState {
    data object Idle : RegisterUiState

    data object Loading : RegisterUiState
}