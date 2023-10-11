package com.sapiest.vaultspace.feature.currencyrates.data.remote

import com.sapiest.vaultspace.core.network.ApiCall
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.NowResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.TimeZone


interface NowService {

    //https://timeapi.io/api/Time/current/zone?timeZone=Europe/Amsterdam
    @GET("/api/Time/current/zone")
    suspend fun getNowTimeByZone(
        @Query("zone") zone: String
    ): ApiCall<NowResponse>
}