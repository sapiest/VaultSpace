package com.sapiest.vaultspace.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sapiest.vaultspace.core.database.converters.RoomTypeConverters
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.CurrencyRatesDatabase
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyDataEntity
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyRateEntity
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyResource

@Database(
    entities = [
        CurrencyDataEntity::class,
        CurrencyRateEntity::class,
        CurrencyResource::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    RoomTypeConverters::class
)
abstract class VaultSpaceDatabase : RoomDatabase(), CurrencyRatesDatabase