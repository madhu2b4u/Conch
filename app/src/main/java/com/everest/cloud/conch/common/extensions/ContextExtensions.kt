package com.everest.cloud.conch.common.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.WindowManager
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

fun Context.dpFromPx(px: Float): Float {
    return px / resources.displayMetrics.density
}

fun Context.pxFromDp(dp: Float): Float {
    return dp * resources.displayMetrics.density
}

fun Context.pxFromDp(@DimenRes dpResID: Int): Float {
    return pxFromDp(resources.getDimension(dpResID))
}

fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}

val Context.launchIntent: Intent?
    get() = packageManager.getLaunchIntentForPackage(packageName)

@SuppressLint("Recycle")
fun Context.getStyledAttributes(
    attributeSet: AttributeSet?,
    styleArray: IntArray,
    block: TypedArray.() -> Unit
) =
    this.obtainStyledAttributes(attributeSet, styleArray, 0, 0).use(block)

@SuppressLint("Recycle")
fun Context.getStyledAttributes(
    attributesResId: Int,
    styleArray: IntArray,
    block: TypedArray.() -> Unit
) =
    this.obtainStyledAttributes(attributesResId, styleArray).use(block)

@SuppressLint("Recycle")
fun Context.getStyledAttributes(attributeArray: IntArray, block: TypedArray.() -> Unit) =
    this.obtainStyledAttributes(attributeArray).use(block)

fun TypedArray.use(block: TypedArray.() -> Unit) {
    try {
        block()
    } finally {
        this.recycle()
    }
}

fun Context.isCameraAvailable(): Boolean =
    packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

fun Context.resolveStyledAttributeRes(@AttrRes attrRes: Int): Int {
    val attrs = intArrayOf(attrRes)
    val ta = obtainStyledAttributes(attrs)
    val resId = ta.getResourceId(0, 0)
    ta.recycle()

    return resId
}

fun Context.checkPermissionGranted(vararg permissions: String): Boolean {
    for (permission in permissions) {
        val granted = ContextCompat.checkSelfPermission(this, permission)
        if (granted != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
val Context.windowManager: WindowManager
    get() = getSystemService(WindowManager::class.java)