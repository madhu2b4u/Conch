package com.everest.cloud.conch.auth.data.repository


import com.everest.cloud.conch.auth.data.remote.source.SignUpRemoteDataSource
import javax.inject.Inject


class SignUpRepositoryImpl @Inject constructor(
    private val remoteDataSource: SignUpRemoteDataSource
) : SignUpRepository {

    /* override suspend fun bookingRequest(request: PnrMobileRequest) = liveData {
         emit(Result.loading())
         try {
             val response = remoteDataSource.bookingRequest(request)
             emit(Result.success(response))

         } catch (exception: Exception) {
             emit(Result.error(exception.message ?: "", null))
         }
     }*/

}