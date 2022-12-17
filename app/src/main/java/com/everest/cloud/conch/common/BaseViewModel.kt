package com.everest.cloud.conch.common

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everest.cloud.conch.common.snackbar.Message
import com.everest.cloud.conch.common.snackbar.SingleLiveEvent
import com.everest.cloud.conch.common.snackbar.SnackbarAlertType
import com.everest.cloud.conch.common.snackbar.SnackbarData
import com.everest.cloud.conch.common.support.*


abstract class BaseViewModel(
    val defaultErrorEvents: DefaultErrorEventsProvider = DefaultErrorEventsProvider()
) : ViewModel(),
    DefaultErrorEvents by defaultErrorEvents,
    LCEEvent {

    protected val lceEventProvider = LCEEventProvider(viewModelScope)
    override val lceState: LiveData<LCEState> get() = lceEventProvider.lceState

    val showSnackbar: LiveData<SnackbarData> get() = _showSnackbar
    val _showSnackbar = SingleLiveEvent<SnackbarData>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val defaultExceptionHandler = DefaultExceptionHandler(defaultErrorEvents)

    fun getDefaultExceptionHandler(customHandler: CustomExceptionHandler? = null) =
        DefaultCoroutineExceptionHandler(
            fallbackHandler = defaultExceptionHandler,
            customHandler = customHandler,
            logger = Logger
        )

    fun showSnackBar(message: Int, alertType: SnackbarAlertType) {
        _showSnackbar.value = SnackbarData.Builder()
            .setAlertType(alertType)
            .setMessage(Message.AsResource(message))
            .build()
    }

    fun showSnackBar(message: String, alertType: SnackbarAlertType) {
        _showSnackbar.value = SnackbarData.Builder()
            .setAlertType(alertType)
            .setMessage(Message.AsString(message))
            .build()
    }

}