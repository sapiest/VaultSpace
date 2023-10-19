
package com.sapiest.vaultspace.core.datastore

import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SafeDataStore @Inject constructor(
    private val encryptedDataStore: SecureDataStore,
    private val unsecureDataStore: UnsecureDataStore
) {

    @Volatile
    private var dataStore: DataStore<Preferences>? = null

    @OptIn(InternalCoroutinesApi::class)
    fun provide(): DataStore<Preferences> = dataStore ?: synchronized(this) {
        dataStore ?: initDataStore().also { dataStore = it }
    }

    private fun initDataStore(): DataStore<Preferences> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                encryptedDataStore.provide()
            } catch (e: Exception) {
                unsecureDataStore.provide()
            }
        } else {
            unsecureDataStore.provide()
        }
    }
}