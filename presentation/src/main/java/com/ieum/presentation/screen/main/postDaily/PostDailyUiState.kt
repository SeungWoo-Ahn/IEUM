package com.ieum.presentation.screen.main.postDaily

sealed class PostDailyUiState {
    data object Idle : PostDailyUiState()

    data object Loading : PostDailyUiState()
}