package com.sapiest.vaultspace.feature.currencyrates.data.impl.remote

import com.sapiest.vaultspace.core.coroutines.AppCoroutineDispatchers
import com.sapiest.vaultspace.core.coroutines.Dispatcher
import com.sapiest.vaultspace.core.network.ServiceProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import javax.inject.Inject

interface NowProvider {
    suspend fun initialize()

    operator fun invoke(): LocalDateTime
}

class NowProviderImpl @Inject constructor(
    private val nowServiceProvider: ServiceProvider<NowService>,
    @Dispatcher(AppCoroutineDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : NowProvider {

    private var now: LocalDateTime? = null
    override suspend fun initialize() {
        now = withContext(ioDispatcher) {
            getNowFromService().getOrNull() ?: LocalDateTime.now(ZoneId.systemDefault())
        }
    }
    override fun invoke(): LocalDateTime = requireNotNull(now) { "Now value hasn`t initialized yet" }

    private suspend fun getNowFromService(): Result<LocalDateTime> {
        val response = nowServiceProvider().getNowTimeByZone(ZoneId.systemDefault().id).execute()

        if (response.isSuccess) {
            val currentDate = response.getOrNull()?.dateTime
                ?: return Result.failure(Exception("Time now is null"))
            return Result.success(LocalDateTime.parse(currentDate))
        } else {
            return Result.failure(
                response.exceptionOrNull() ?: Exception("Unknown error from $TAG")
            )
        }
    }

    private companion object {
        const val TAG = "NowProviderImpl"
    }
}