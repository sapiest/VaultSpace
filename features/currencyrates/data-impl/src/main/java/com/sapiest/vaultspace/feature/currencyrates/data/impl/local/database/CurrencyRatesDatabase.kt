package com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database

import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyDataDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyRatesDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyResourceDao
import com.sapiest.vaultspace.feature.currencyrates.data.impl.local.database.dao.CurrencyTimeStampDao

interface CurrencyRatesDatabase {

    fun currencyDataDao(): CurrencyDataDao

    fun currencyRatesDao(): CurrencyRatesDao

    fun currencyResourceDao(): CurrencyResourceDao

    fun currencyTimestampDao(): CurrencyTimeStampDao
}