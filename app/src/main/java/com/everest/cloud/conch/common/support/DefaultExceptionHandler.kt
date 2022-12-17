package com.everest.cloud.conch.common.support

import com.everest.cloud.conch.common.extensions.isNetworkUnAuthorizeOrForbidden
import com.everest.cloud.conch.common.snackbar.Message
import retrofit2.HttpException

// todo - tests
@Suppress("FunctionName")
fun DefaultExceptionHandler(errorEvents: DefaultErrorEventsProvider) =
    ExceptionHandler {
        when (it) {
            is ReadableNetworkException -> errorEvents.showError(Message.AsString(it.error))
            is BaseNetworkException, is HttpException -> handleNetworkException(it, errorEvents)
            else -> errorEvents.showGeneralError()
        }
        true
    }

private fun handleNetworkException(ex: Throwable, errorEvents: DefaultErrorEventsProvider) {
    if (ex.isNetworkUnAuthorizeOrForbidden()) {
        errorEvents.showUnauthorizedError()
        return
    }
    errorEvents.showGeneralError()
}