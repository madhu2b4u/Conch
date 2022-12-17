package com.everest.cloud.conch.common.support

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import androidx.annotation.IdRes

fun ViewFlipper.showView(@IdRes viewId: Int?) {
    if (viewId != null && currentView.id != viewId) {
        val childView = findViewById<View>(viewId)
        childView.show()
        displayedChild = indexOfChild(childView)
    }
}

fun ViewFlipper.showView(view: View) {
    if (currentView.id != view.id) {
        view.show()
        displayedChild = indexOfChild(view)
    }
}

fun ViewFlipper.enableAnimation() {
    inAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    outAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
}

fun ViewFlipper.disableAnimation() {
    inAnimation = null
    outAnimation = null
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}