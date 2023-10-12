package com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import org.threeten.bp.LocalDateTime

@Entity(tableName = "currency_data")
data class CurrencyDataEntity(

    @PrimaryKey
    @CurrencyCode
    val name: String,

    val lastUpdate: LocalDateTime
)