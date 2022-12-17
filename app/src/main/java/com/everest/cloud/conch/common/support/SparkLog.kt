package com.everest.cloud.conch.common.support


interface SparkLog {
    fun i(tag: String, message: String)

    fun d(tag: String, message: String)

    fun w(tag: String, message: String)

    fun e(tag: String, message: String)

    fun e(tag: String, message: String, throwable: Throwable?)
}