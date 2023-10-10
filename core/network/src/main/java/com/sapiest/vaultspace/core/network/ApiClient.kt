package com.sapiest.vaultspace.core.network

import retrofit2.Retrofit
import javax.inject.Inject

interface ApiClient {

    fun <T> createService(serviceClass: Class<T>): T
}

class ApiClientImpl @Inject constructor(
    private val retrofitBuilder: Retrofit
): ApiClient {

    override fun <T> createService(serviceClass: Class<T>): T {
        return retrofitBuilder.create(serviceClass)
    }
}