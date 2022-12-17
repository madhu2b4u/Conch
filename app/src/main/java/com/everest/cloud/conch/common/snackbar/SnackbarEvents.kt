package com.everest.cloud.conch.common.snackbar

import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import com.google.android.datatransport.runtime.dagger.Provides
import javax.inject.Singleton

interface SnackbarEvents {
    val showSnackBar: LiveData<SnackbarData>
    val showGlobalSnackBar: LiveData<SnackbarData>
    val dismissSnackBar: LiveData<Unit>
}

@Singleton
class SnackbarEventsProvider : SnackbarEvents {
    override val showSnackBar: LiveData<SnackbarData> get() = _showSnackbar
    private val _showSnackbar = SingleLiveEvent<SnackbarData>()

    override val showGlobalSnackBar: LiveData<SnackbarData> get() = _showGlobalSnackBar
    private val _showGlobalSnackBar = SingleLiveEvent<SnackbarData>()

    override val dismissSnackBar: LiveData<Unit> get() = _dismissSnackBar
    private val _dismissSnackBar = SingleLiveEvent<Unit>()


    @AnyThread
    @Provides
    fun show(snackbarData: SnackbarData) {
        _showSnackbar.postValue(snackbarData)
    }

    @AnyThread
    @Provides
    fun showAsGlobal(snackbarData: SnackbarData) {
        _showGlobalSnackBar.postValue(snackbarData)
    }

    @AnyThread
    @Provides
    fun dismiss() {
        _dismissSnackBar.postValue(Unit)
    }
}