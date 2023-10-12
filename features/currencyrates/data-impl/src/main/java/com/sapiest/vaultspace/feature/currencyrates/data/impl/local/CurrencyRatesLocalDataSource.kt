package com.sapiest.vaultspace.feature.currencyrates.data.impl.local

import com.sapiest.vaultspace.core.database.providers.DaoProvider
import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.data.api.models.CurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyDataDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyRatesDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyResourceDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyTimeStampDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models.CurrencyDataEntity
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models.CurrencyRateEntity
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models.CurrencyResource
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models.CurrencyTimeStampEntity
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models.toCurrencyModel
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.models.toListCurrencyModel
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class CurrencyRatesLocalDataSource @Inject constructor(
    private val currencyRatesDao: DaoProvider<CurrencyRatesDao>,
    private val currencyDataDao: DaoProvider<CurrencyDataDao>,
    private val currencyResourceDao: DaoProvider<CurrencyResourceDao>,
    private val currencyTimeStampDao: DaoProvider<CurrencyTimeStampDao>
) {
    suspend fun insertCurrencyData(currencyDataEntity: CurrencyDataEntity) =
        currencyDataDao().insertCurrencyData(currencyDataEntity)

    suspend fun insertCurrencyRate(currencyRateEntity: CurrencyRateEntity) =
        currencyRatesDao().insertCurrencyRates(currencyRateEntity)

    suspend fun insertAllCurrencyResources(currencyResources: List<CurrencyResource>) =
        currencyResourceDao().insertAllCurrencyResources(currencyResources)

    suspend fun getLastUpdateTimestamp(): LocalDateTime {
        return currencyTimeStampDao().getLastUpdateTimestamp()?.lastUpdate ?: LocalDateTime.MIN
    }

    suspend fun updateLastUpdateTimestamp(timestamp: LocalDateTime) {
        currencyTimeStampDao().insertOrUpdateLastUpdate(CurrencyTimeStampEntity(lastUpdate = timestamp))
    }

    suspend fun fetchCurrencyFromCache(@CurrencyCode name: String): Result<CurrencyModel> {
        return kotlin.runCatching {
            val currencyResource = currencyResourceDao().getCurrencyResource(name)
            requireNotNull(currencyResource) { "CurrencyResource with $name not found" }
            currencyResource.toCurrencyModel()
        }
    }

    suspend fun fetchCurrenciesFromCache(): Result<List<CurrencyModel>> {
        return kotlin.runCatching {
            val currencyResourceList =
                currencyResourceDao().getAllCurrencyResource()
            requireNotNull(currencyResourceList) { "CurrencyResourceList is empty" }
            currencyResourceList.toListCurrencyModel()
        }
    }
}