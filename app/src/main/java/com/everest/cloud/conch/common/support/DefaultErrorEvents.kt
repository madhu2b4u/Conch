package com.everest.cloud.conch.common.support

import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import com.everest.cloud.conch.common.snackbar.Message
import com.everest.cloud.conch.common.snackbar.SingleLiveEvent

interface DefaultErrorEvents {
    val showError: LiveData<Message>
    val showGeneralError: LiveData<Unit>
    val showNetworkError: LiveData<Unit>
    val showUnauthorizedError: LiveData<Unit>
    val hideError: LiveData<Unit>
}

class DefaultErrorEventsProvider :
    DefaultErrorEvents {
    override val showError: LiveData<Message> get() = _showError
    private val _showError = SingleLiveEvent<Message>()

    override val showGeneralError: LiveData<Unit> get() = _showGeneralError
    private val _showGeneralError = SingleLiveEvent<Unit>()

    override val showNetworkError: LiveData<Unit> get() = _showNetworkError
    private val _showNetworkError = SingleLiveEvent<Unit>()

    override val showUnauthorizedError: LiveData<Unit> get() = _showUnauthorizedError
    private val _showUnauthorizedError = SingleLiveEvent<Unit>()

    override val hideError: LiveData<Unit> get() = _hideError
    private val _hideError = SingleLiveEvent<Unit>()

    fun showError(error: Message) {
        _showError.value = error
    }

    fun showGeneralError() {
        _showGeneralError.call()
    }

    fun showNetworkError() {
        _showNetworkError.call()
    }

    fun showUnauthorizedError() {
        _showUnauthorizedError.call()
    }

    @AnyThread
    fun hide() {
        _hideError.postValue(Unit)
    }
}