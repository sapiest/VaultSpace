package com.sapiest.vaultspace.feature.currencyrates.data

import com.sapiest.vaultspace.core.coroutines.AppCoroutineDispatchers
import com.sapiest.vaultspace.core.coroutines.Dispatcher
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.common.models.CurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.data.local.CurrencyRatesLocalDataSource
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.toCurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.toListCurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.data.remote.CurrencyRatesRemoteDataSource
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.CurrencyDataResponse
import com.sapiest.vaultspace.feature.currencyrates.data.remote.models.toCurrencyList
import kotlinx.coroutines.CoroutineDispatcher
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
    @Dispatcher(AppCoroutineDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher,
    @Dispatcher(AppCoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : CurrencyRateRepository {

    private val now = LocalDateTime.now()

    override suspend fun getCurrency(
        @CurrencyCode name: String,
        forceUpdate: Boolean
    ): Result<CurrencyModel> {
        val lastUpdate = currencyRatesLocalDataSource.getLastUpdateTimestamp()

        return if (!forceUpdate && lastUpdate.plusDays(1).isAfter(now)
        ) {
            fetchCurrenciesFromCache(name)
        } else {
            saveCurrencyToCache { getCurrenciesFromServer() }.getOrNull()
                ?.find { it.name == name }
                ?.run { Result.success(this) }
                ?: return Result.failure(Exception("Currency $name not found"))
        }
    }

    override suspend fun getMyCurrencies(
        names: List<String>,
        forceUpdate: Boolean
    ): Result<List<CurrencyModel>> {
        val lastUpdate = currencyRatesLocalDataSource.getLastUpdateTimestamp()

        return if (!forceUpdate && lastUpdate.plusDays(1).isAfter(now)
        ) {
            fetchCurrenciesFromCache(names)
        } else {
            saveCurrencyToCache { getCurrenciesFromServer() }.getOrNull()
                ?.filter { it.name in names }
                ?.run { Result.success(this) }
                ?: return Result.failure(Exception("Currencies $names not found"))
        }
    }

    private suspend fun fetchCurrenciesFromCache(@CurrencyCode name: String): Result<CurrencyModel> {
        return kotlin.runCatching {
            val currencyResource = currencyRatesLocalDataSource.getCurrencyResource(name)
            requireNotNull(currencyResource) { "CurrencyResource with $name not found" }
            currencyResource.toCurrencyModel()
        }
    }

    private suspend fun fetchCurrenciesFromCache(names: List<@CurrencyCode String>): Result<List<CurrencyModel>> {
        return kotlin.runCatching {
            val currencyResourceList =
                currencyRatesLocalDataSource.getAllCurrencyResources()?.filter {
                    it.currencyData.name in names
                }
            requireNotNull(currencyResourceList) { "CurrencyResourceList with $names not found" }
            currencyResourceList.toListCurrencyModel()
        }
    }

    private suspend fun saveCurrencyToCache(serverRequest: suspend () -> Result<List<CurrencyDataResponse>>): Result<List<CurrencyModel>> {
        return try {
            val currencyModelResult = serverRequest.invoke()
            if (currencyModelResult.isSuccess) {
                val currencyData = currencyModelResult.getOrNull()?.toCurrencyList() ?: emptyList()
                currencyRatesLocalDataSource.insertAllCurrencyResources(currencyData)
                Result.success(currencyData.toListCurrencyModel())
            } else {
                Result.failure(currencyModelResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getCurrenciesFromServer(): Result<List<CurrencyDataResponse>> {
        val response = currencyRatesRemoteDataSource.getCurrentRates().execute()

        if (response.isSuccess) {
            val currencyList = response.getOrNull()?.results
                ?: return Result.failure(Exception("No currency data"))
            return Result.success(currencyList)
        } else {
            return Result.failure(response.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}