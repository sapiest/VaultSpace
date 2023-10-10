package com.sapiest.vaultspace.feature.currencyrates.data.di

import androidx.room.RoomDatabase
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.CurrencyRatesDatabase
import com.sapiest.vaultspace.feature.currencyrates.data.remote.CurrencyRatesRemoteDataSource
import com.sapiest.vaultspace.feature.currencyrates.data.remote.CurrencyRatesRemoteDataSourceImpl
import com.sapiest.vaultspace.feature.currencyrates.data.remote.CurrencyRatesService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsCurrencyRatesRemoteDataSource(
        currencyRatesRemoteDataSource: CurrencyRatesRemoteDataSourceImpl
    ): CurrencyRatesRemoteDataSource

    @Binds
    @Reusable
    fun bindCurrencyRatesDatabase(
        database: RoomDatabase
    ): CurrencyRatesDatabase
}