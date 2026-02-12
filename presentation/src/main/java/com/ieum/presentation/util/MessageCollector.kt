package com.ieum.presentation.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

object MessageCollector {
    private val messageChannel = Channel<String>(Channel.BUFFERED)

    val messageFlow: Flow<String> = messageChannel.receiveAsFlow()

    suspend fun sendMessage(message: String) {
        messageChannel.send(message)
    }
}