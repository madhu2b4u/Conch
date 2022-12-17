package com.everest.cloud.conch.common

import android.content.res.Configuration
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.everest.cloud.conch.R
import com.everest.cloud.conch.common.snackbar.SnackbarAlertType


abstract class BaseActivity : AppCompatActivity() {

    private val sharedPrefsHelpers = SpUtil.instance
    private val networkListener: NetworkListener by lazy { NetworkListener() }
    var isNetworkAvailable = false

    var miniRedAlert = SnackbarAlertType.MINOR_ALERT
    var miniGreenAlert = SnackbarAlertType.MINOR_POSITIVE_INFORMATION
    var majorRedAlert = SnackbarAlertType.MAJOR_ALERT

    var userName = sharedPrefsHelpers?.getString(NAME)
    var userMobile = sharedPrefsHelpers?.getString(PHONE)
    var userEmail = sharedPrefsHelpers?.getString(EMAIL)
    var userId = sharedPrefsHelpers?.getString(USER_ID)

    override fun onResume() {
        super.onResume()
        if (isNightMode()){
            Log.e("LightDark", "night")
        }else{
            Log.e("LightDark", "morning")
        }


    }

    open fun isNightMode(): Boolean {
        val nightModeFlags: Int = getResources().configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

}