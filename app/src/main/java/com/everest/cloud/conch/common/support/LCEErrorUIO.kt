package com.everest.cloud.conch.common.support

import com.everest.cloud.conch.R


data class LCEErrorUIO(
    val title: Text,
    val subTitle: Text,
    val onActionClicked: () -> Unit,
    val actionText: Text = Text.AsResource(R.string.try_again)
) {
    override fun equals(other: Any?) = other is LCEErrorUIO && Data(other) == Data(this)
    override fun hashCode(): Int = Data(this).hashCode()

    private data class Data(
        val title: Text,
        val subTitle: Text,
        val actionText: Text
    ) {
        constructor(uio: LCEErrorUIO) : this(uio.title, uio.subTitle, uio.actionText)
    }
}

interface LceErrorDataBinder {
    fun bind(error: LCEErrorUIO)
}