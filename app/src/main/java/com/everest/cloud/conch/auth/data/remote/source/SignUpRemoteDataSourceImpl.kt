package com.everest.cloud.conch.auth.data.remote.source

import com.everest.cloud.conch.auth.data.remote.services.SignUpService
import com.everest.cloud.conch.di.qualifiers.IO
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class SignUpRemoteDataSourceImpl @Inject constructor(
    private val service: SignUpService,
    @IO private val context: CoroutineContext
) : SignUpRemoteDataSource {

    /*override suspend fun bookingRequest(request: PnrMobileRequest)= withContext(context) {
        val response = service.requestBookingDetailsAsync(request).await()
        if (response.isSuccessful)
            response.body() ?: throw Exception("no user")
        else
            throw Exception("invalid request with code + response.code()")
    }*/
}
