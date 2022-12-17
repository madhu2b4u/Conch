package com.everest.cloud.conch.common.support

import android.content.Context
import androidx.annotation.StringRes
import com.everest.cloud.conch.common.snackbar.Message
import java.io.Serializable

sealed class Text : Serializable {

    data class AsResource(@StringRes val res: Int) : Serializable, Text()
    data class AsString(val title: String) : Serializable, Text()

    fun getValue(context: Context): String = when (this) {
        is AsResource -> context.resources.getString(res)
        is AsString -> title
    }

    companion object {
        fun toText(message: Message): Text {
            return when (message) {
                is Message.AsResource -> AsResource(message.res)
                is Message.AsString -> AsString(message.message)
            }
        }
    }
}
