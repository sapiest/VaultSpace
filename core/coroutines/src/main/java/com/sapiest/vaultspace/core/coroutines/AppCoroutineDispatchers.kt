package com.sapiest.vaultspace.core.coroutines

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val appDispatchers: AppCoroutineDispatchers)

enum class AppCoroutineDispatchers {
    Default,
    IO,
    Main,
}