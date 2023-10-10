package com.sapiest.vaultspace.feature.currencyrates.data.di

import com.sapiest.vaultspace.core.database.providers.DaoProvider
import com.sapiest.vaultspace.core.network.ServiceProvider
import com.sapiest.vaultspace.core.network.getServiceProvider
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.CurrencyRatesDatabase
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.dao.CurrencyDataDao
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.dao.CurrencyRatesDao
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.dao.CurrencyResourceDao
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.dao.CurrencyTimeStampDao
import com.sapiest.vaultspace.feature.currencyrates.data.remote.CurrencyRatesService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyRatesModule {

    @Provides
    @Singleton
    fun provideCurrencyRatesService(
        retrofit: Retrofit
    ): ServiceProvider<CurrencyRatesService> =
        getServiceProvider {
            retrofit.create(CurrencyRatesService::class.java)
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