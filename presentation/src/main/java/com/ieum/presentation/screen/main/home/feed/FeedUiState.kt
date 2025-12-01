package com.ieum.presentation.screen.main.home.feed

sealed class FeedUiState {
    data object Idle : FeedUiState()

    data object ShowAddPostDialog : FeedUiState()
}