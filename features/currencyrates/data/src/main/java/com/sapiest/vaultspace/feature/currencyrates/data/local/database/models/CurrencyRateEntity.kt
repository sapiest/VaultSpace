package com.sapiest.vaultspace.feature.currencyrates.data.local.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.common.models.CurrencyRateModel
import org.threeten.bp.LocalDateTime

@Entity(
    tableName = "currency_rate",
    foreignKeys = [
        ForeignKey(
            entity = CurrencyDataEntity::class,
            parentColumns = ["name"],
            childColumns = ["parentCurrencyName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CurrencyRateEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @CurrencyCode
    val name: String,

    val rate: Double,
    val lastUpdate: LocalDateTime,

    @CurrencyCode
    val parentCurrencyName: String
)

fun CurrencyRateEntity.toCurrencyRateModel() = CurrencyRateModel(
    name = name,
    rate = rate,
    lastUpdate = lastUpdate
)