package com.sapiest.vaultspace.feature.currencyrates.usecase.impl

import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.api.CurrencyRateRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository
) {
    suspend operator fun invoke(names: List<@CurrencyCode String>, forceUpdate: Boolean = false) =
        currencyRateRepository.getMyCurrencies(names, forceUpdate)
}