package com.sapiest.vaultspace.feature.currencyrates.data

import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.common.models.CurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.data.local.CurrencyRatesLocalDataSource
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.toListCurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.data.remote.CurrencyRatesRemoteDataSource
import com.sapiest.vaultspace.feature.currencyrates.data.remote.NowProvider
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.CurrencyDataResponse
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.toCurrencyList
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

interface CurrencyRateRepository {

    suspend fun getCurrency(
        @CurrencyCode name: String,
        forceUpdate: Boolean = false
    ): Result<CurrencyModel>

    suspend fun getMyCurrencies(
        names: List<@CurrencyCode String>,
        forceUpdate: Boolean = false
    ): Result<List<CurrencyModel>>
}

class CurrencyRateRepositoryImpl @Inject constructor(
    private val currencyRatesLocalDataSource: CurrencyRatesLocalDataSource,
    private val currencyRatesRemoteDataSource: CurrencyRatesRemoteDataSource,
    private val nowProvider: NowProvider
) : CurrencyRateRepository {

    private val now: LocalDateTime
        get() = nowProvider()

    private suspend fun shouldFetchFromServer(forceUpdate: Boolean): Boolean {
        val lastUpdate = currencyRatesLocalDataSource.getLastUpdateTimestamp()
        return forceUpdate || lastUpdate.plusDays(1).isAfter(now)
    }

    override suspend fun getCurrency(
        @CurrencyCode name: String,
        forceUpdate: Boolean
    ): Result<CurrencyModel> {
        return fetchData(forceUpdate) { it.name == name }?.firstOrNull()
            ?.run { Result.success(this) }
            ?: return Result.failure(Exception("Currency $name not found"))
    }

    override suspend fun getMyCurrencies(
        names: List<@CurrencyCode String>,
        forceUpdate: Boolean
    ): Result<List<CurrencyModel>> {
        return fetchData(forceUpdate) { it.name in names }?.run { Result.success(this) }
            ?: return Result.failure(Exception("Currencies $names not found"))
    }


    private suspend fun fetchData(
        forceUpdate: Boolean,
        predicate: (CurrencyModel) -> Boolean
    ): List<CurrencyModel>? {
        if (shouldFetchFromServer(forceUpdate)) {
            val currencyModelResult =
                saveCurrencyToCache { currencyRatesRemoteDataSource.getCurrenciesFromServer() }
            if (currencyModelResult.isSuccess) {
                return currencyModelResult.getOrNull()?.filter(predicate)
            }
            return null
        } else {
            return currencyRatesLocalDataSource.fetchCurrenciesFromCache().getOrNull()
                ?.filter(predicate)
        }
    }

    private suspend fun saveCurrencyToCache(serverRequest: suspend () -> Result<List<CurrencyDataResponse>>): Result<List<CurrencyModel>> {
        return try {
            val currencyModelResult = serverRequest.invoke()
            val currencyData = currencyModelResult.getOrNull()?.toCurrencyList() ?: emptyList()
            currencyRatesLocalDataSource.insertAllCurrencyResources(currencyData)
            Result.success(currencyData.toListCurrencyModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}