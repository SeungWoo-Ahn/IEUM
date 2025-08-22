package com.ieum.presentation.screen.main.postDailyRecords

sealed class PostDailyRecordsEvent {
    data object MoveBack : PostDailyRecordsEvent()
}