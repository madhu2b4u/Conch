package com.everest.cloud.conch.common.support

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ThrottleLiveData<T> constructor(
    private val coroutineScope: CoroutineScope,
    private val delayInMillis: Long = LiveDataThrottlingDelayProvider.getDelayInMillis(),
    private val dispatcher: AppCoroutineDispatchers = AppCoroutineDispatcherProvider.dispatcher()
) : MutableLiveData<T>() {
    private var currentJob: Job? = null

    override fun postValue(value: T) {
        currentJob?.cancel()
        currentJob = coroutineScope.launch(dispatcher.io()) {
            delay(delayInMillis)
            withContext(dispatcher.main()) {
                super.postValue(value)
            }
        }
    }
}

const val DEFAULT_THROTTLING_DELAY: Long = 100

object LiveDataThrottlingDelayProvider {
    fun getDelayInMillis(): Long = DEFAULT_THROTTLING_DELAY
}