package com.everest.cloud.conch

import android.app.Application
import com.everest.cloud.conch.common.SpUtil
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ConchApp : Application() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate() {
        super.onCreate()
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        SpUtil.instance?.init(this)
    }
}