package com.everest.cloud.conch.common.support

import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope

interface LCEEvent {
    val lceState: LiveData<LCEState>
}

class LCEEventProvider(scope: CoroutineScope) : LCEEvent {
    override val lceState: LiveData<LCEState> get() = _lceState
    private val _lceState: ThrottleLiveData<LCEState> = ThrottleLiveData(scope)

    // holds latest state. _lceState can be a little bit out of sync bcz of throttling
    var currentState: LCEState? = null
        private set

    @AnyThread
    fun setLCEState(state: LCEState) {
        currentState = state
        _lceState.postValue(state)
    }
}

sealed class LCEState {
    object Loading : LCEState()
    data class Error(val data: LCEErrorUIO) : LCEState()
    object Content : LCEState()
    data class NoContent(val data: LCENoContentUIO?) : LCEState()
}
