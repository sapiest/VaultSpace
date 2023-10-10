package com.sapiest.vaultspace.feature.currencyrates.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyDataEntity

@Dao
interface CurrencyDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyData(currencyData: CurrencyDataEntity)

    @Query("SELECT * FROM currency_data WHERE name = :name")
    suspend fun getCurrencyDataByName(@CurrencyCode name: String): CurrencyDataEntity
}