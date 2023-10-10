package com.sapiest.vaultspace.feature.currencyrates.data.remote.models

import com.sapiest.vaultspace.feature.currencyrates.common.CurrencyCode
import com.sapiest.vaultspace.feature.currencyrates.common.models.CurrencyRateModel
import com.sapiest.vaultspace.feature.currencyrates.data.local.database.models.CurrencyRateEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

@Serializable
data class CurrencyDataResponse(

    @CurrencyCode
    @SerialName("T")
    private val ticker: String,

    @SerialName("o")
    val openPrice: Double,

    @SerialName("c")
    val closePrice: Double,

    @SerialName("t")
    private val timestamp: Long
) {
    val lastUpdate: LocalDateTime
        get() = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()

    val name: String
        get() = ticker.split(":")[1].substring(3)

    val parentCurrencyName: String
        get() = ticker.split(":")[1].substring(0, 3)
}

fun CurrencyDataResponse.toCurrencyRateModel() = CurrencyRateModel(
    name = name,
    rate = closePrice,
    lastUpdate = lastUpdate
)

fun CurrencyDataResponse.toCurrencyRateEntity() = CurrencyRateEntity(
    name = name,
    rate = closePrice,
    lastUpdate = lastUpdate,
    parentCurrencyName = parentCurrencyName
)