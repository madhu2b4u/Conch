package com.everest.cloud.conch.common.support

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

fun AppBarLayout.requestLayoutOnCollapseStateChange(viewToRequestLayout: View): AppBarLayout.OnOffsetChangedListener {
    val listener = object : AppBarLayout.OnOffsetChangedListener {
        private var prevCollapsedState: Boolean? = null
        override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
            val collapsed = abs(verticalOffset) - appBarLayout.totalScrollRange == 0
            if (collapsed != prevCollapsedState) {
                viewToRequestLayout.requestLayout()
                prevCollapsedState = collapsed
            }
        }
    }
    addOnOffsetChangedListener(listener)
    return listener
}

fun AppBarLayout.expand(animate: Boolean = false) {
    setExpanded(true, animate)
}

fun AppBarLayout.setAppBarDragging(canDrag: Boolean) {
    val params = layoutParams as? CoordinatorLayout.LayoutParams ?: return
    val behavior = AppBarLayout.Behavior()
    behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
        override fun canDrag(appBarLayout: AppBarLayout) = canDrag
    })
    params.behavior = behavior
}


fun AppBarLayout.disableDraggingAndExpandedBehavior() {
    setAppBarDragging(false)
    setExpanded(false)
}