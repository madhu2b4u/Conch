package com.everest.cloud.conch.common.support

interface ExceptionHandler {
    fun handle(exception: Throwable): Boolean
}

@Suppress("FunctionName")
inline fun ExceptionHandler(crossinline handler: (Throwable) -> Boolean): ExceptionHandler =
    object : ExceptionHandler {
        override fun handle(exception: Throwable): Boolean {
            return handler.invoke(exception)
        }
    }

@Throws
inline fun <T, reified E> throwSpecificException(
    handleException: (ex: E) -> Unit,
    methodCall: () -> T
): T {
    return try {
        methodCall.invoke()
    } catch (throwable: Throwable) {
        if (throwable is E) {
            handleException(throwable)
        }
        throw throwable
    }
}