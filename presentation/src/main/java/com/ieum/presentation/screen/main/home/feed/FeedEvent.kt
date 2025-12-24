package com.ieum.presentation.screen.main.home.feed

sealed class FeedEvent {
    data object TogglePostLike : FeedEvent()
}