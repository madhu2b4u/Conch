package com.everest.cloud.conch.auth.presentation.ui.fragments


import com.everest.cloud.conch.R
import com.everest.cloud.conch.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_signin
    }
}
