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

    suspend fun getAllCurrencies(
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
        if (forceUpdate) return true
        val lastUpdate = currencyRatesLocalDataSource.getLastUpdateTimestamp()
        return lastUpdate.plusDays(1).isAfter(now)
    }

    override suspend fun getCurrency(
        @CurrencyCode name: String,
        forceUpdate: Boolean
    ): Result<CurrencyModel> {
        return fetchData(forceUpdate) { it.name == name }
            .mapCatching {
                it.firstOrNull() ?: throw NoSuchElementException("Currency $name not found")
            }
    }

    override suspend fun getMyCurrencies(
        names: List<@CurrencyCode String>,
        forceUpdate: Boolean
    ): Result<List<CurrencyModel>> {
        return fetchData(forceUpdate) { it.name in names }
            .onFailure {
                return Result.failure(Exception("Currencies $names not found or ${it.message}"))
            }
    }

    override suspend fun getAllCurrencies(forceUpdate: Boolean): Result<List<CurrencyModel>> {
        return fetchData(forceUpdate)
            .onFailure {
                return Result.failure(Exception("Currencies not found"))
            }
    }

    private suspend fun fetchData(
        forceUpdate: Boolean,
        predicate: (CurrencyModel) -> Boolean = { true }
    ): Result<List<CurrencyModel>> {
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