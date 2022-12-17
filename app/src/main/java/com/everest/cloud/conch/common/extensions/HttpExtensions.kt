package com.everest.cloud.conch.common.support

import com.everest.cloud.conch.common.extensions.is4xx
import com.everest.cloud.conch.common.extensions.isForbiddenCode
import com.everest.cloud.conch.common.extensions.isUnauthorizedCode
import okhttp3.HttpUrl
import okhttp3.Request
import retrofit2.HttpException

fun HttpUrl.origin() = "$scheme://$host"

fun HttpException.isUnauthorized() = code().isUnauthorizedCode()
fun HttpException.isForbidden() = code().isForbiddenCode()
fun HttpException.is4xx() = code().is4xx()

internal const val HEADER_TOKEN_KEY = "Authorization"

fun Request.Builder.addAuthorizationHeader(tokenValue: String) {
    addHeader(HEADER_TOKEN_KEY, tokenValue)
}

fun Request.Builder.replaceAuthorizationHeader(
    tokenValue: String
) {
    removeHeader(HEADER_TOKEN_KEY)
    addAuthorizationHeader(tokenValue)
}

val Request.accessToken: String?
    get() = header(HEADER_TOKEN_KEY)