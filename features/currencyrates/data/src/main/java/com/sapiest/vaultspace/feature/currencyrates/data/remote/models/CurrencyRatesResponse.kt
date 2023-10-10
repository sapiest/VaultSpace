package com.sapiest.vaultspace.feature.currencyrates.data.remote.models

import com.sapiest.vaultspace.feature.currencyrates.common.models.CurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.common.models.CurrencyRateModel
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyDataEntity
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyRateEntity
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyResource
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.threeten.bp.LocalDateTime

@Serializable
data class CurrencyRatesResponse(

    @SerialName("request_id")
    val requestId: String,

    @SerialName("status")
    val status: String,

    @SerialName("results")
    val results: @Contextual List<CurrencyDataResponse>
)


fun List<CurrencyDataResponse>.toCurrencyList(): List<CurrencyResource> {
    // Группируем данные по базовой валюте
    val groupedByBaseCurrency = this.groupBy {
        it.parentCurrencyName
    }

    return groupedByBaseCurrency.map { (baseCurrency, results) ->
        CurrencyResource(
            currencyData = CurrencyDataEntity(
                baseCurrency,
                LocalDateTime.now()
            ),
            rates = results.map {
                CurrencyRateEntity(
                    name = it.name,
                    rate = it.closePrice,
                    lastUpdate = it.lastUpdate,
                    parentCurrencyName = baseCurrency
                )
            }
        )
    }
}