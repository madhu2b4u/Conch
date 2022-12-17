package com.everest.cloud.conch.di

import android.content.ContentValues
import android.util.Log
import com.everest.cloud.conch.BuildConfig
import com.everest.cloud.conch.common.SpUtil
import com.everest.cloud.conch.common.TOKEN
import com.everest.cloud.conch.di.qualifiers.IO
import com.everest.cloud.conch.di.qualifiers.MainThread
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val sharedPrefUtil = SpUtil.instance!!

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient().newBuilder()
        client.connectTimeout(60, TimeUnit.SECONDS)
        client.readTimeout(60, TimeUnit.SECONDS)
        if (!sharedPrefUtil.getString(TOKEN, "").isNullOrEmpty()) {
            Log.e(ContentValues.TAG, "onCreate: ${sharedPrefUtil.getString(TOKEN)}")
            client.addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader(
                        "Authorization",
                        "Bearer " + sharedPrefUtil.getString(TOKEN, "").toString()
                    )

                    .build()
                chain.proceed(newRequest)
            }
        }
        client.addInterceptor(logging)
        return client.build()
    }

    private fun getUrl(): String {
        return if (BuildConfig.DEBUG) {
            BuildConfig.BASE_URL
        } else BuildConfig.PROD_URL
    }

    /*private fun provideOksHttpClient(): OkHttpClient? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient().newBuilder()
        client.connectTimeout(60, TimeUnit.SECONDS)
        client.readTimeout(60, TimeUnit.SECONDS)
        if (!sharedPrefUtil?.getString(TOKEN, "").isNullOrEmpty()) {
            Log.e(ContentValues.TAG, "onCreate: ${sharedPrefUtil?.getString(TOKEN,"")}")
            client.addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("Authorization", sharedPrefUtil?.getString(TOKEN, "").toString())

                    .build()
                chain.proceed(newRequest)
            }
        }
        client.addInterceptor(logging)

        return client.build()
    }*/


    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getUrl())
            .client(okHttpClient)
            .build()
    }

    @IO
    @Provides
    fun providesIoDispatcher(): CoroutineContext = Dispatchers.IO

    @MainThread
    @Provides
    fun providesMainThreadDispatcher(): CoroutineContext = Dispatchers.Main

}