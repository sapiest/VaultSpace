package com.sapiest.vaultspace.feature.currencyrates.data.impl.di

import com.sapiest.vaultspace.core.database.providers.DaoProvider
import com.sapiest.vaultspace.core.network.ServiceProvider
import com.sapiest.vaultspace.core.network.getServiceProvider
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.CurrencyRatesDatabase
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyDataDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyRatesDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyResourceDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyTimeStampDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.CurrencyRatesService
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.NowService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyRatesModule {

    @Provides
    @Singleton
    @Named("nowApi")
    fun provideNowApiRetrofit(
        retrofit: Retrofit
    ): Retrofit = retrofit.newBuilder().baseUrl("https://timeapi.io/api").build()


    @Provides
    @Singleton
    @Named("currencyApi")
    fun provideCurrencyApiRetrofit(
        retrofit: Retrofit
    ): Retrofit = retrofit.newBuilder().baseUrl("https://api.polygon.io").build()

    @Provides
    @Singleton
    fun provideCurrencyRatesService(
        @Named("currencyApi") retrofit: Retrofit
    ): ServiceProvider<CurrencyRatesService> =
        getServiceProvider {
            retrofit.create(CurrencyRatesService::class.java)
        }

    @Provides
    @Singleton
    fun provideNowService(
        @Named("nowApi") retrofit: Retrofit
    ): ServiceProvider<NowService> =
        getServiceProvider {
            retrofit.create(NowService::class.java)
        }

    @Provides
    @Reusable
    fun provideCurrencyDataDao(
        database: CurrencyRatesDatabase
    ): DaoProvider<CurrencyDataDao> = DaoProvider { database.currencyDataDao() }

    @Provides
    @Reusable
    fun provideCurrencyResourceDao(
        database: CurrencyRatesDatabase
    ): DaoProvider<CurrencyResourceDao> = DaoProvider { database.currencyResourceDao() }

    @Provides
    @Reusable
    fun provideCurrencyRatesDao(
        database: CurrencyRatesDatabase
    ): DaoProvider<CurrencyRatesDao> = DaoProvider { database.currencyRatesDao() }

    @Provides
    @Reusable
    fun provideCurrencyTimestampDao(
        database: CurrencyRatesDatabase
    ): DaoProvider<CurrencyTimeStampDao> = DaoProvider { database.currencyTimestampDao() }
}