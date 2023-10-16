package com.sapiest.vaultspace.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sapiest.vaultspace.core.network.ApiClient
import com.sapiest.vaultspace.core.network.ApiClientImpl
import com.sapiest.vaultspace.core.network.BuildConfig
import com.sapiest.vaultspace.core.network.interceptors.CurrencyKeyInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideJson() = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    //if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                    //}
                }
        )
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        json: Json,
        okhttpCallFactory: Call.Factory,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .callFactory(okhttpCallFactory)
        .build()


    @Singleton
    @Provides
    @Named("currencyApi")
    fun provideApiKeyInterceptor(
        apiKeyInterceptorFactory: CurrencyKeyInterceptor.Factory
    ): Interceptor {
        return apiKeyInterceptorFactory.create(BuildConfig.CURRENCY_API_KEY)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface NetworkClientModule {

    @Singleton
    @Binds
    fun bindsApiClient(
        apiClient: ApiClientImpl
    ): ApiClient
}