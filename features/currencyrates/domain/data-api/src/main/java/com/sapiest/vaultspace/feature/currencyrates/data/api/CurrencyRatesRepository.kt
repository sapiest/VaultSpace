package com.sapiest.vaultspace.feature.currencyrates.data.api

import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.api.models.CurrencyModel

interface CurrencyRateRepository {

    suspend fun getCurrency(
        @CurrencyCode name: String,
        forceUpdate: Boolean = false
    ): Result<CurrencyModel>

    suspend fun getMyCurrencies(
        names: List<@CurrencyCode String>,
        forceUpdate: Boolean = false
    ): Result<List<CurrencyModel>>

    suspend fun getAllCurrencies(
        forceUpdate: Boolean = false
    ): Result<List<CurrencyModel>>
}