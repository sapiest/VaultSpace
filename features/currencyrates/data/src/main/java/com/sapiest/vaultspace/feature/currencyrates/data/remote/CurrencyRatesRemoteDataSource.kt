package com.sapiest.vaultspace.feature.currencyrates.data.remote

import com.sapiest.vaultspace.core.network.ApiCall
import com.sapiest.vaultspace.core.network.ServiceProvider
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.CurrencyDataResponse
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.CurrencyRatesResponse
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

interface CurrencyRatesRemoteDataSource {

    private val currentDate: String
        get() = LocalDate.now().minusDays(1).run {
            format(DateTimeFormatter.ISO_LOCAL_DATE)
        }

    suspend fun getCurrentRates(date: String = currentDate): ApiCall<CurrencyRatesResponse>

    suspend fun getLastRate(
        @CurrencyCode parentCurrencyName: String,
        @CurrencyCode relatedCurrencyName: String
    ): ApiCall<CurrencyDataResponse>
}

class CurrencyRatesRemoteDataSourceImpl @Inject constructor(
    private val retrofitServiceProvider: ServiceProvider<CurrencyRatesService>
) : CurrencyRatesRemoteDataSource {

    override suspend fun getCurrentRates(date: String): ApiCall<CurrencyRatesResponse> {
        return retrofitServiceProvider().getCurrentRates(date)
    }

    override suspend fun getLastRate(
        @CurrencyCode parentCurrencyName: String,
        @CurrencyCode relatedCurrencyName: String
    ): ApiCall<CurrencyDataResponse> {
        return retrofitServiceProvider().getLastRate("$parentCurrencyName:$relatedCurrencyName")
    }

}

