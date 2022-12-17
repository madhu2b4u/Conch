package com.everest.cloud.conch.common.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Browser
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.everest.cloud.conch.R
import java.io.IOException

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}


fun String.toNumericString() = this.filter { it.isDigit() }

fun Activity.openActivityRight() {
    overridePendingTransition(R.anim.enter_slide_right, R.anim.exit_slide_left)
}

fun Activity.exitActivityBottom() {
    finish()
    overridePendingTransition(R.anim.enter_slide_bottom, R.anim.exit_slide_bottom)
}

fun Activity.exitActivityLeft() {
    finish()
    overridePendingTransition(R.anim.enter_slide_left, R.anim.exit_slide_right)
}

fun Activity.openInCustomTab(string: String) = customTabsWeb(string)

private fun Context.customTabsWeb(string: String) {
    try {

        val builder = CustomTabsIntent.Builder()

        builder.setToolbarColor(
            Color.parseColor(
                "#" + Integer.toHexString(
                    ContextCompat.getColor(
                        this,
                        R.color.redCardBackground
                    )
                )
            )
        )
        builder.setShowTitle(true)
        builder.setExitAnimations(this, R.anim.enter_slide_left, R.anim.exit_slide_left)
        builder.setStartAnimations(this, R.anim.enter_slide_right, R.anim.exit_slide_right)

        val intent = builder.build()
        intent.launchUrl(this, Uri.parse(string))

    } catch (e: IOException) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(string))
        intent.putExtra(Browser.EXTRA_CREATE_NEW_TAB, true)
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, packageName)
        startActivity(intent)
    }
}

fun noCrash(enableLog: Boolean = true, func: () -> Unit): String? {
    return try {
        func()
        null
    } catch (e: Exception) {
        if (enableLog)
            e.printStackTrace()
        e.message
    }
}

fun TextView.simpletext(value: String) {
    this.text = value
}

fun getLastBitFromUrl(url: String): String =
    url.replaceFirst(".*/([^/?]+).*".toRegex(), "$1")

fun ViewGroup.inflate(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

class ContextHandler
    (private val context: Context) {
    val appContext: Context get() = context.applicationContext
}

inline val buildIsMAndLower: Boolean
    get() = Build.VERSION.SDK_INT <= Build.VERSION_CODES.M

@SuppressLint("NewApi")
fun Context.checkNetworkState(): Boolean {
    val connectivityManager =
        this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (!buildIsMAndLower) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        val nw = connectivityManager.activeNetworkInfo ?: return false
        return when (nw.type) {
            (NetworkCapabilities.TRANSPORT_WIFI) -> true
            (NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}
