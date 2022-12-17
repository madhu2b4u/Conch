package com.everest.cloud.conch.common.support

import androidx.annotation.Keep
import com.everest.cloud.conch.common.extensions.is4xx
import com.everest.cloud.conch.common.extensions.isForbiddenCode
import com.everest.cloud.conch.common.extensions.isUnauthorizedCode
import okhttp3.ResponseBody

@Keep
open class BaseNetworkException : Exception() {
    //This field cannot be part of constructor and have default value as Moshi is using reflection based on field names.
    //That is why this needs to be set asap after object creation.
    @Transient
    var rawResponse: RawResponse? = null
        internal set
}

fun BaseNetworkException.isUnauthorized() = rawResponse?.code?.isUnauthorizedCode() == true
fun BaseNetworkException.isForbidden() = rawResponse?.code?.isForbiddenCode() == true
fun BaseNetworkException.is4xx() = rawResponse?.code?.is4xx() == true

@Keep
class RawResponse(val code: Int, @Suppress("unused") val errorBody: ResponseBody?)

sealed class NetworkCommunicationException(cause: Throwable) :
    Exception("Network error: $cause", cause) {
    class ConnectionIssue(cause: Throwable) : NetworkCommunicationException(cause)
}

@Keep
open class ReadableNetworkException(val error: String) : BaseNetworkException()