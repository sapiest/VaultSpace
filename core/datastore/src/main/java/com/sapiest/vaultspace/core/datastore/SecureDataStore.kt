package com.sapiest.vaultspace.core.datastore

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import com.sapiest.vaultspace.core.coroutines.AppCoroutineDispatchers
import com.sapiest.vaultspace.core.coroutines.Dispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import java.io.File
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.M)
class SecureDataStore @Inject internal constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(AppCoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    private val dataStoreFile =
        File(context.filesDir, "encrypted_data_store_file")

    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val encryptedDataStoreFile = {
        if (!dataStoreFile.exists()) {
            EncryptedFile.Builder(
                context,
                dataStoreFile,
                masterKey,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()
        }
        dataStoreFile
    }

    fun provide(): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = encryptedDataStoreFile,
        scope = CoroutineScope(ioDispatcher)
    )
}