package com.sapiest.vaultspace.feature.currencyrates.data.impl.di

import androidx.room.RoomDatabase
import com.sapiest.vaultspace.feature.currencyrates.data.api.CurrencyRateRepository
import com.sapiest.vaultspace.feature.currencyrates.data.impl.CurrencyRateRepositoryImpl
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.CurrencyRatesDatabase
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.NowProvider
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.NowProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CurrencyRatesBinder {

    @Binds
    @Singleton
    fun bindNowProvider(
        nowProviderImpl: NowProviderImpl
    ): NowProvider

    @Binds
    @Reusable
    fun bindCurrencyRatesDatabase(
        database: RoomDatabase
    ): CurrencyRatesDatabase

    @Provides
    @Reusable
    fun provideCurrencyRateRepository(
        currencyRateRepository: CurrencyRateRepositoryImpl
    ): CurrencyRateRepository
}