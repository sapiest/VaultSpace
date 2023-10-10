package com.sapiest.vaultspace.feature.currencyrates.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyDataEntity
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyRateEntity
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyResource

@Dao
interface CurrencyResourceDao {
    @Transaction
    @Query("SELECT * FROM currency_data")
    suspend fun getAllCurrencyResource(): List<CurrencyResource>?

    @Transaction
    @Query("SELECT * FROM currency_data WHERE name = :currencyName")
    suspend fun getCurrencyResource(@CurrencyCode currencyName: String): CurrencyResource?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencyData(currencies: List<CurrencyDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencyRates(rates: List<CurrencyRateEntity>)

    @Transaction
    suspend fun insertAllCurrencyResources(resources: List<CurrencyResource>) {
        val currencyDataList = resources.map { it.currencyData }
        insertAllCurrencyData(currencyDataList)

        val allRates = resources.flatMap { it.rates }
        insertAllCurrencyRates(allRates)
    }
}