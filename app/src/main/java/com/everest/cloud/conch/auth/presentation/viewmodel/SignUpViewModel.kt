package com.everest.cloud.conch.auth.presentation.viewmodel

import androidx.lifecycle.LifecycleObserver
import com.everest.cloud.conch.auth.domain.SignUpUseCase
import com.everest.cloud.conch.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val mSignUpUseCase: SignUpUseCase
) : BaseViewModel(), LifecycleObserver