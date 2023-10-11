package com.sapiest.vaultspace.feature.currencyrates.common.models

import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import org.threeten.bp.LocalDateTime

data class CurrencyModel(
    @CurrencyCode
    val name: String,
    val ratesList: List<CurrencyRateModel>
)


data class CurrencyRateModel(
    @CurrencyCode
    val name: String,
    val rate: Double,
    val lastUpdate: LocalDateTime,
)