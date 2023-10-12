package com.sapiest.vaultspace.feature.currencyrates.data.impl

import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.api.CurrencyRateRepository
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.CurrencyRatesLocalDataSource
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models.toListCurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.CurrencyRatesRemoteDataSource
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.NowProvider
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.models.CurrencyDataResponse
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.models.toCurrencyList
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class CurrencyRateRepositoryImpl @Inject constructor(
    private val currencyRatesLocalDataSource: CurrencyRatesLocalDataSource,
    private val currencyRatesRemoteDataSource: CurrencyRatesRemoteDataSource,
    private val nowProvider: NowProvider
) : CurrencyRateRepository {

    private val now: LocalDateTime
        get() = nowProvider()

    private suspend fun shouldFetchFromServer(forceUpdate: Boolean): Boolean {
        if (forceUpdate) return true
        val lastUpdate = currencyRatesLocalDataSource.getLastUpdateTimestamp()
        return lastUpdate.plusDays(1).isAfter(now)
    }

    override suspend fun getCurrency(
        @CurrencyCode name: String,
        forceUpdate: Boolean
    ): Result<com.sapiest.vaultspace.feature.currencyrates.data.api.models.CurrencyModel> {
        return fetchData(forceUpdate) { it.name == name }
            .mapCatching {
                it.firstOrNull() ?: throw NoSuchElementException("Currency $name not found")
            }
    }

    override suspend fun getMyCurrencies(
        names: List<@CurrencyCode String>,
        forceUpdate: Boolean
    ): Result<List<com.sapiest.vaultspace.feature.currencyrates.data.api.models.CurrencyModel>> {
        return fetchData(forceUpdate) { it.name in names }
            .onFailure {
                return Result.failure(Exception("Currencies $names not found or ${it.message}"))
            }
    }

    override suspend fun getAllCurrencies(forceUpdate: Boolean): Result<List<com.sapiest.vaultspace.feature.currencyrates.data.api.models.CurrencyModel>> {
        return fetchData(forceUpdate)
            .onFailure {
                return Result.failure(Exception("Currencies not found"))
            }
    }

    private suspend fun fetchData(
        forceUpdate: Boolean,
        predicate: (com.sapiest.vaultspace.feature.currencyrates.data.api.models.CurrencyModel) -> Boolean = { true }
    ): Result<List<com.sapiest.vaultspace.feature.currencyrates.data.api.models.CurrencyModel>> {
        return if (shouldFetchFromServer(forceUpdate)) {
            val currencyModelResult =
                saveCurrencyToCache { currencyRatesRemoteDataSource.getCurrenciesFromServer() }
            currencyModelResult.map { it.filter(predicate) }
        } else {
            currencyRatesLocalDataSource.fetchCurrenciesFromCache().map {
                it.filter(predicate)
            }
        }
    }

    private suspend fun saveCurrencyToCache(serverRequest: suspend () -> Result<List<CurrencyDataResponse>>): Result<List<com.sapiest.vaultspace.feature.currencyrates.data.api.models.CurrencyModel>> {
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