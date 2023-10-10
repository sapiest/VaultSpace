package com.sapiest.vaultspace.feature.currencyrates.data.local.database.models

import androidx.room.Embedded
import androidx.room.Relation
import com.sapiest.vaultspace.feature.currencyrates.common.models.CurrencyModel

data class CurrencyResource(
    @Embedded val currencyData: CurrencyDataEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "parentCurrencyName"
    )
    val rates: List<CurrencyRateEntity>
)

fun CurrencyResource.toCurrencyModel() = CurrencyModel(
    name = currencyData.name,
    ratesList = rates.map { it.toCurrencyRateModel() }
)

fun List<CurrencyResource>.toListCurrencyModel() = this.map { it.toCurrencyModel() }