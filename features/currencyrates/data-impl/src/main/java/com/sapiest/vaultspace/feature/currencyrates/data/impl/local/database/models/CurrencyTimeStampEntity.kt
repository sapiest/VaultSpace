package com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity(tableName = "currency_timestamp_table")
data class CurrencyTimeStampEntity(

    @PrimaryKey
    val id: Int = 1,

    val lastUpdate: LocalDateTime
)