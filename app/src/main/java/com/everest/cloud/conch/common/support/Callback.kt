package com.everest.cloud.conch.common.support

interface Callback<T> {
    fun onComplete(result: T)
    fun onException(e: Exception?)
}
