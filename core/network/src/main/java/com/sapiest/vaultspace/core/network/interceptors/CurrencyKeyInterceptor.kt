package com.sapiest.vaultspace.core.network.interceptors

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import okhttp3.Interceptor
import okhttp3.Response

class CurrencyKeyInterceptor @AssistedInject constructor(
    @Assisted private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("apiKey", apiKey)
            .build()

        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(requestWithApiKey)
    }

    @AssistedFactory
    interface Factory {
        fun create(apiKey: String): CurrencyKeyInterceptor
    }
}