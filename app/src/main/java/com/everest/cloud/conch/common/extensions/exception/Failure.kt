package com.everest.cloud.conch.common.extensions.exception

sealed class Failure {
    class NetworkConnection : Failure()
    class ServerError : Failure()
    data class CustomError(val errorCode: Int, val errorMessage: String?) : Failure()
}