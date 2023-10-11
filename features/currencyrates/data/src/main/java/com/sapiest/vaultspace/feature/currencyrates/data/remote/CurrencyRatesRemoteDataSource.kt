package com.sapiest.vaultspace.feature.currencyrates.data.remote

import com.sapiest.vaultspace.core.network.ApiCall
import com.sapiest.vaultspace.core.network.ServiceProvider
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.CurrencyDataResponse
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.CurrencyRatesResponse
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class CurrencyRatesRemoteDataSource @Inject constructor(
    private val retrofitServiceProvider: ServiceProvider<CurrencyRatesService>,
    private val nowServiceProvider: NowProvider
) {

    private val currentDate: String
        get() = nowServiceProvider().minusDays(1).run {
            format(DateTimeFormatter.ISO_LOCAL_DATE)
        }

    suspend fun getCurrentRates(date: String = currentDate): ApiCall<CurrencyRatesResponse> {
        return retrofitServiceProvider().getCurrentRates(date)
    }

    suspend fun getLastRate(
        @CurrencyCode parentCurrencyName: String,
        @CurrencyCode relatedCurrencyName: String
    ): ApiCall<CurrencyDataResponse> {
        return retrofitServiceProvider().getLastRate("$parentCurrencyName:$relatedCurrencyName")
    }

    suspend fun getCurrenciesFromServer(date: String = currentDate): Result<List<CurrencyDataResponse>> {
        val response = retrofitServiceProvider().getCurrentRates(date).execute()

        if (response.isSuccess) {
            val currencyList = response.getOrNull()?.results
                ?: return Result.failure(Exception("No currency data"))
            return Result.success(currencyList)
        } else {
            return Result.failure(response.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}

