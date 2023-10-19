package com.sapiest.vaultspace.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.sapiest.vaultspace.core.coroutines.AppCoroutineDispatchers
import com.sapiest.vaultspace.core.coroutines.Dispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import java.io.File
import javax.inject.Inject

class UnsecureDataStore @Inject internal constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(AppCoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    private val dataStoreFile =
        File(context.filesDir, "data_store_file")

    fun provide(): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { dataStoreFile },
        scope = CoroutineScope(ioDispatcher)
    )
}