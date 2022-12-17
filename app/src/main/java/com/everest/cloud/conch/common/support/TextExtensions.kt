package com.everest.cloud.conch.common.support

import android.widget.TextView
import java.util.*

fun TextView.showWithTextOrHide(
    text: String?
) {
    when {
        text.isNullOrEmpty() -> hide()
        else -> {
            setText(text)
            show()
        }
    }
}


fun String.isNotEmptyAndNotBlank(): Boolean {
    return isNotEmpty() && isNotBlank()
}

fun String.toLowerCaseWithDefaultLocale(): String {
    return lowercase(Locale.getDefault())
}
