package com.everest.cloud.conch.auth.presentation.ui.fragments


import android.graphics.Paint
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.everest.cloud.conch.R
import com.everest.cloud.conch.auth.presentation.viewmodel.SignUpViewModel
import com.everest.cloud.conch.common.BaseFragment
import com.everest.cloud.conch.common.extensions.noCrash
import com.everest.cloud.conch.common.support.show
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.NonDisposableHandle.parent


@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    private var fcmToken = ""

    private val viewModel: SignUpViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.fragment_signup
    }

    private fun generateFcmToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("FCM", "FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }
                val token1: String = task.result
                fcmToken = token1
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            noCrash {
                setDefaultViews()
                generateFcmToken()
                observeOnViewModel()
            }
        }
    }
    private fun setDefaultViews() {
        tvSkip.paintFlags = tvSkip.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btnSignUp.setOnClickListener {
            otpNumberLayout.slideDown(1000)

        }


    }

    private fun observeOnViewModel() = with(viewModel) {
        lifecycle.addObserver(this)
    }

    fun View.slideUp(duration: Int = 500) {
        visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

    fun View.slideDown(duration: Int = 500) {
        visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }



}
