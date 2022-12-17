package com.everest.cloud.conch.common.support

object Logger : SparkLog {
    private lateinit var logImpl: SparkLog

    fun init(log: SparkLog) {
        logImpl = log
    }

    override fun i(tag: String, message: String) {
        logImpl.i(tag, message)
    }

    override fun d(tag: String, message: String) {
        logImpl.d(tag, message)
    }

    override fun w(tag: String, message: String) {
        logImpl.w(tag, message)
    }

    override fun e(tag: String, message: String) {
        logImpl.e(tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        logImpl.e(tag, message, throwable)
    }
}