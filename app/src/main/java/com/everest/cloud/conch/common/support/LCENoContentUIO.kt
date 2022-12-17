package com.everest.cloud.conch.common.support

data class LCENoContentUIO(val message: Text)

interface LCENoContentDataBinder {
    fun bind(data: LCENoContentUIO)
}