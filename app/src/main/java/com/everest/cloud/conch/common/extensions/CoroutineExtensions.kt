package com.everest.cloud.conch.common.support

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> awaitCallback(block: (Callback<T>) -> Unit): T =
    suspendCancellableCoroutine { cont ->
        block(object : Callback<T> {
            override fun onComplete(result: T) = cont.resume(result)
            override fun onException(e: Exception?) {
                e?.let { cont.resumeWithException(it) }
            }
        })
    }

fun <T> runBlockingSafely(
    exceptionMessage: String = "Exception thrown",
    block: suspend CoroutineScope.() -> T
): T? {
    return try {
        return runBlocking {
            block()
        }
    } catch (ex: Exception) {
        Logger.e("runBlockingSafely", exceptionMessage, ex)
        null
    }
}

suspend fun <T> runSafely(
    failureAction: (suspend () -> Unit)? = null,
    action: suspend () -> T
): T? {
    return try {
        action.invoke()
    } catch (ex: Throwable) {
        Logger.e("runSafely", "Exception thrown", ex)
        failureAction?.invoke()
        null
    }
}

typealias DefaultExceptionHandler = ExceptionHandler

typealias CustomExceptionHandler = ExceptionHandler

@Suppress("FunctionName")
fun DefaultCoroutineExceptionHandler(
    fallbackHandler: DefaultExceptionHandler,
    customHandler: CustomExceptionHandler? = null,
    logger: SparkLog? = null
): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            logger?.e("ExceptionHandler", "throw exception", exception)
            if (customHandler?.handle(exception) == true) return
            fallbackHandler.handle(exception)
        }
    }