package com.everest.cloud.conch.common.support

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.everest.cloud.conch.common.snackbar.*
import com.google.android.material.snackbar.Snackbar

open class DefaultErrorEventsViewDelegate(
    private val defaultErrorEvents: DefaultErrorEvents
) : UserInteractionListener(), LifecycleObserver {

    override fun onUserInteracted(eventType: UserInteractionType) {
        snackBar?.dismiss()
    }

    protected var snackBar: Snackbar? = null

    fun observe(fragment: Fragment) {
        observe(fragment.viewLifecycleOwner, fragment.requireView())
    }

    fun observe(viewLifecycleOwner: LifecycleOwner, viewToAttach: View) {
        defaultErrorEvents.showError.observe(viewLifecycleOwner, Observer {
            it?.let { showErrorSnackbar(viewToAttach, it) }
        })
        defaultErrorEvents.showGeneralError.observe(viewLifecycleOwner, Observer {
            showGeneralErrorSnackbar(viewToAttach)
        })
        defaultErrorEvents.showNetworkError.observe(viewLifecycleOwner, Observer {
            showNetworkErrorSnackbar(viewToAttach)
        })
        defaultErrorEvents.showUnauthorizedError.observe(viewLifecycleOwner, Observer {
            showGeneralErrorSnackbar(viewToAttach)
        })
        defaultErrorEvents.hideError.observe(viewLifecycleOwner, Observer {
            snackBar?.dismiss()
        })
    }

    private fun showErrorSnackbar(viewToAttach: View, error: Message) {
        snackBar = viewToAttach.makeErrorSnackbar(error).apply { show() }
    }

    open fun showGeneralErrorSnackbar(viewToAttach: View) {
        snackBar = viewToAttach.makeGeneralErrorSnackbar().apply { show() }
    }

    private fun showNetworkErrorSnackbar(viewToAttach: View) {
        snackBar = viewToAttach.makeNetworkErrorSnackbar().apply { show() }
    }
}