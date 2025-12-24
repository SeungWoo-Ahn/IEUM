package com.ieum.presentation.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object GlobalEventBus {
    private val _eventFlow = MutableSharedFlow<GlobalEvent>(extraBufferCapacity = 1)
    val eventFlow: SharedFlow<GlobalEvent> = _eventFlow.asSharedFlow()

    suspend fun emitGlobalEvent(event: GlobalEvent) {
        _eventFlow.emit(event)
    }
}

sealed class GlobalEvent {
    data object AddMyPost : GlobalEvent()
}