package com.sapiest.vaultspace.feature.currencyrates.domain

import com.sapiest.vaultspace.feature.currencyrates.data.CurrencyRateRepository
import javax.inject.Inject

class GetAllCurrenciesUseCase @Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean) =
        currencyRateRepository.getAllCurrencies(forceUpdate)
}