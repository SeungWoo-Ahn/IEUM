package com.ieum.presentation.screen.main.postDaily

sealed class PostDailyEvent {
    data object MoveBack : PostDailyEvent()
}