package com.everest.cloud.conch.auth.domain

import com.everest.cloud.conch.auth.data.repository.SignUpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCaseImpl @Inject constructor(private val mSignUpRepository: SignUpRepository) :
    SignUpUseCase
