package com.everest.cloud.conch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.everest.cloud.conch.language.LanguageActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateToIntroOrLoginOrHome()
    }


    private fun navigateToIntroOrLoginOrHome() {
        val activityScope = CoroutineScope(Dispatchers.Main)
        activityScope.launch {
            delay(1000)
            navigateToIntro()
        }
    }

    private fun navigateToIntro() {
        val intent = Intent(this@MainActivity, LanguageActivity::class.java)
        startActivity(intent)
        finish()
    }
}