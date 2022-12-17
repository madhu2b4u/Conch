package com.everest.cloud.conch.language

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.everest.cloud.conch.R
import com.everest.cloud.conch.auth.presentation.ui.activity.AuthActivity
import com.everest.cloud.conch.common.BaseActivity
import kotlinx.android.synthetic.main.activity_language.*


class LanguageActivity : BaseActivity() {

    private val array = arrayOf("English", "Telugu", "Tamil", "Hindi", "Malayalam")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)
        setAdapter()
        submitChosenLanguage()
        showCustomUI()
    }

    private fun setAdapter() {
        val adapter = SpinnerAdapter(this, array)
        spinnerLanguage.setAdapter(adapter)
        spinnerLanguage.onItemClickListener =
            AdapterView.OnItemClickListener { adapter, _, position, id ->
                val selectedValue = adapter.getItemAtPosition(position).toString()
            }
    }

    private fun showCustomUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun submitChosenLanguage() {
        btnLanguageNext.setOnClickListener {
            navigateToAuthentication()
        }
    }

    private fun navigateToAuthentication() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        //finish()
    }
}