package com.ieum.presentation.screen.main.postDailyRecords

sealed class PostDailyRecordsUiState {
    data object Idle : PostDailyRecordsUiState()

    data object Loading : PostDailyRecordsUiState()
}