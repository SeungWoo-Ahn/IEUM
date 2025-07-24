package com.ieum.domain.exception

sealed class NetworkException(override val message: String) : IllegalStateException(message) {
    class ClientSide(message: String) : NetworkException(message)
    class ServerSide(message: String) : NetworkException(message)
    class Unknown(message: String) : NetworkException(message)
}

sealed class SGISException(override val message: String) : IllegalStateException(message) {
    class UnAuthorized(message: String) : SGISException(message)
    class Unknown(message: String) : SGISException(message)
}