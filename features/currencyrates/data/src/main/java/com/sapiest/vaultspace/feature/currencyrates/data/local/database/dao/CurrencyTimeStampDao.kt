package com.sapiest.vaultspace.feature.currencyrates.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyTimeStampEntity

@Dao
interface CurrencyTimeStampDao {

    @Query("SELECT * FROM currency_timestamp_table WHERE id = :id LIMIT 1")
    suspend fun getLastUpdateTimestamp(id: Int = 1): CurrencyTimeStampEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateLastUpdate(timestamp: CurrencyTimeStampEntity)
}