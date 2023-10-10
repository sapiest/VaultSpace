package com.sapiest.vaultspace.feature.currencyrates.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyRateEntity

@Dao
interface CurrencyRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRates(rate: CurrencyRateEntity)

    @Query("SELECT * FROM currency_rate WHERE parentCurrencyName = :name")
    suspend fun getRatesByCurrencyName(@CurrencyCode name: String): List<CurrencyRateEntity>
}