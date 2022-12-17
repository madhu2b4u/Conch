package com.everest.cloud.conch.auth.di

import com.everest.cloud.conch.auth.data.repository.SignUpRepository
import com.everest.cloud.conch.auth.data.repository.SignUpRepositoryImpl
import com.everest.cloud.conch.auth.domain.SignUpUseCase
import com.everest.cloud.conch.auth.domain.SignUpUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SignUpDomainModule {

    @Binds
    abstract fun bindsRepository(
        repoImpl: SignUpRepositoryImpl
    ): SignUpRepository


    @Binds
    abstract fun bindsArticlesUseCase(
        mSignUpUseCase: SignUpUseCaseImpl
    ): SignUpUseCase


}