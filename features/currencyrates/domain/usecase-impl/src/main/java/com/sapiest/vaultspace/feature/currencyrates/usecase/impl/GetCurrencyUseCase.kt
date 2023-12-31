package com.sapiest.vaultspace.feature.currencyrates.usecase.impl

import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.api.CurrencyRateRepository
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository
) {
    suspend operator fun invoke(@CurrencyCode name: String, forceUpdate: Boolean = false) =
        currencyRateRepository.getCurrency(name, forceUpdate)
}