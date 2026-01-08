package com.ieum.presentation.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

object GlobalEventCollector {
    private val globalEventChannel = Channel<GlobalEvent>(Channel.BUFFERED)
    val globalEventFlow: Flow<GlobalEvent> = globalEventChannel.receiveAsFlow()

    suspend fun sendGlobalEvent(event: GlobalEvent) {
        globalEventChannel.send(event)
    }
}

sealed class GlobalEvent {
    data object AddMyPost : GlobalEvent()
}