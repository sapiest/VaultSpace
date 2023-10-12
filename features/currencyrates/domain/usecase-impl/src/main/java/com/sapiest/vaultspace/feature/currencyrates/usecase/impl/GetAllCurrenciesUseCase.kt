package com.sapiest.vaultspace.feature.currencyrates.usecase.impl

import com.sapiest.vaultspace.feature.currencyrates.data.api.CurrencyRateRepository
import javax.inject.Inject

class GetAllCurrenciesUseCase @Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean) =
        currencyRateRepository.getAllCurrencies(forceUpdate)
}