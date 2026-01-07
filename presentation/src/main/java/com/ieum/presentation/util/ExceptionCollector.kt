package com.ieum.presentation.util

import com.ieum.domain.exception.NetworkException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

class CustomException(override val message: String) : RuntimeException(message)

@Singleton
class ExceptionCollector @Inject constructor() {
    private val exceptionChannel = Channel<Throwable>()
    val exceptionMessageFlow: Flow<String> =
        exceptionChannel
            .receiveAsFlow()
            .map {
                when (it) {
                    is NetworkException -> {
                        when (it) {
                            is NetworkException.ConnectionException -> "네트워크 연결이 불안정합니다"
                            is NetworkException.ResponseException -> it.message
                            is NetworkException.UnknownException -> "알 수 없는 네트워크 문제가 발생했습니다"
                        }
                    }
                    is CustomException -> it.message
                    else -> "알 수 없는 문제가 발생했습니다"
                }
            }

    suspend fun sendException(t: Throwable) {
        exceptionChannel.send(t)
    }
}