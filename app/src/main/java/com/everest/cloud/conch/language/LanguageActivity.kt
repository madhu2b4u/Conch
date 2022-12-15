package com.everest.cloud.conch.language

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.everest.cloud.conch.R
import kotlinx.android.synthetic.main.activity_language.*


class LanguageActivity : AppCompatActivity() {

    val array = arrayOf("English", "Telugu", "Tamil", "Hindi", "Malayalam")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_language)
        setViews()
        setAdapter()
    }

    private fun setViews() {
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }


    private fun setAdapter(){
        val adapter = SpinnerAdapter(this, array)

        spinnerLanguage.setAdapter(adapter)

        spinnerLanguage.onItemClickListener =
            AdapterView.OnItemClickListener { adapter, v, position, id ->
                Toast.makeText(this, adapter.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
        }
    }
}