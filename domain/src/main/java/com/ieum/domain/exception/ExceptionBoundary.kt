package com.ieum.domain.exception

sealed class NetworkException : RuntimeException() {
    class ConnectionException : NetworkException()
    class ResponseException(override val message: String) : NetworkException()
    class UnknownException(val t: Throwable) : NetworkException()
}

sealed class SGISException : RuntimeException() {
    class UnAuthorized : SGISException()
    class Unknown : SGISException()
}