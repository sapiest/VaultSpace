package com.sapiest.vaultspace.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.sapiest.vaultspace.feature.currencyrates.data.api.CurrencyRateRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class SyncCurrencyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val currencyRatesRepository: CurrencyRateRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            currencyRatesRepository.getAllCurrencies(true)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        /**
         * Expedited one time work to sync data on app startup
         */
        fun startUpSyncWork(workManager: WorkManager) = workManager.enqueueUniquePeriodicWork(
            workerName,
            ExistingPeriodicWorkPolicy.KEEP,
            DelegatingWorker.delegatePeriodicWork<SyncCurrencyWorker>(24, TimeUnit.HOURS)
        )

        // This name should not be changed otherwise the app may have concurrent sync requests running
        internal const val workerName = "SyncCurrencyWorker"
    }
}