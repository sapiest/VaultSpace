package com.sapiest.vaultspace

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sapiest.vaultspace.feature.currencyrates.data.impl.remote.NowProvider
import com.sapiest.vaultspace.sync.workers.init.SyncInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class VaultSpaceApp: Application() {

    @Inject
    lateinit var nowProvider: NowProvider

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // Обработка исключений здесь
        // Например: Timber.e(throwable, "Caught exception in coroutine.")
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        AndroidThreeTen.init(this)
        super.onCreate()

        GlobalScope.launch(Dispatchers.Default + coroutineExceptionHandler){
            SyncInitializer.initialize(this@VaultSpaceApp)
            nowProvider.initialize()
        }
    }
}