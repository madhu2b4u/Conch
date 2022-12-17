package com.everest.cloud.conch.auth.di


import com.everest.cloud.conch.auth.data.remote.services.SignUpService
import com.everest.cloud.conch.auth.data.remote.source.SignUpRemoteDataSource
import com.everest.cloud.conch.auth.data.remote.source.SignUpRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module(includes = [SignUpRemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class SignUpRemoteModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: SignUpRemoteDataSourceImpl
        ): SignUpRemoteDataSource
    }

    @Provides
    fun providesSignUpService(retrofit: Retrofit): SignUpService =
        retrofit.create(SignUpService::class.java)
}