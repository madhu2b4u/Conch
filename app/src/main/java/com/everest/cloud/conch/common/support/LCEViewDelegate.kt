package com.everest.cloud.conch.common.support

import android.widget.ViewFlipper
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.layout_error.view.*
import kotlinx.android.synthetic.main.layout_no_content.view.*

class LCEViewDelegate(
    private val events: LCEEvent,
    private val views: Views,
    lifecycleScope: LifecycleCoroutineScope
) {
    private var isAppbarRestored = false

    init {
        lifecycleScope.launchWhenStarted {
            // Logic should be in started lifecycle block bcz onRestoreInstanceState()
            // is called before started lifecycle and we are pretty sure about
            // restored state
        }
    }

    private var currentState: LCEState? = null
    fun observe(lifecycleOwner: LifecycleOwner) {
        events.lceState.observe(lifecycleOwner, {
            it?.let { updateState(it) }
        })
    }

    private fun updateState(state: LCEState) {
        when (state) {
            LCEState.Loading -> views.viewFlipper.showView(views.loaderResId)
            LCEState.Content -> views.viewFlipper.showView(views.contentResId)
            is LCEState.NoContent -> {
                setupNoContent(state)
                views.viewFlipper.showView(views.noContentResId)
            }
            is LCEState.Error -> {
                setupError(state)
                views.viewFlipper.showView(views.errorResId)
            }
        }.exhaustive
        currentState = state
    }

    private fun setupNoContent(state: LCEState.NoContent) {
        val context = views.viewFlipper.context
        state.data?.message?.let { it ->
            views.viewFlipper.noContentView.text = it.getValue(context)
        }
    }

    private fun setupError(state: LCEState.Error) {
        val context = views.viewFlipper.context
        val data = state.data
        with(views.viewFlipper) {
            errorTitleText.showWithTextOrHide(data.title.getValue(context))
            errorSubTitleText.showWithTextOrHide(data.subTitle.getValue(context))
            errorButton.apply {
                showWithTextOrHide(data.actionText.getValue(context))
                setOnClickListener { data.onActionClicked() }
            }
        }
    }

    class Views(
        val viewFlipper: ViewFlipper,
        @IdRes val loaderResId: Int,
        @IdRes val errorResId: Int?,
        @IdRes val contentResId: Int,
        @IdRes val noContentResId: Int? = null,
    )
}

val <T> T.exhaustive: T
    get() = this

