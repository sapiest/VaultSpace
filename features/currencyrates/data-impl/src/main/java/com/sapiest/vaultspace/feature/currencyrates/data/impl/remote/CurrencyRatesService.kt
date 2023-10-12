package com.sapiest.vaultspace.feature.currencyrates.data.impl.remote

import com.sapiest.vaultspace.core.network.ApiCall
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.models.CurrencyDataResponse
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.models.CurrencyRatesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyRatesService {

    @GET("/v2/aggs/grouped/locale/global/market/fx/{date}")
    suspend fun getCurrentRates(
        // format YYYY-MM-DD. Api return value only for previous date
        @Path("date") date: String
    ): ApiCall<CurrencyRatesResponse>

    @GET("/v2/aggs/ticker/C:{ticker}/prev")
    suspend fun getLastRate(
        @CurrencyCode
        @Path("ticker") ticker: String
    ): ApiCall<CurrencyDataResponse>
}

