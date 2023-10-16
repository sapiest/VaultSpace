package com.sapiest.vaultspace.sync.workers.init

import android.app.Application
import androidx.work.WorkManager
import com.sapiest.vaultspace.sync.workers.SyncCurrencyWorker
import kotlinx.coroutines.Dispatchers

object SyncInitializer {
    // This method is initializes sync, the process that keeps the app's data current.
    // It is called from the app module's Application.onCreate() and should be only done once.
    suspend fun initialize(context: Application) = with(Dispatchers.Default) {
        val workManager = WorkManager.getInstance(context)
        // Run sync on app startup and ensure only one sync worker runs at any time
        SyncCurrencyWorker.startUpSyncWork(workManager)
    }
}
