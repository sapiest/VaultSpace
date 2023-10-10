package com.sapiest.vaultspace.core.network

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


inline fun <T> getServiceProvider(crossinline provider: suspend () -> T): ServiceProvider<T> {
    return object : ServiceProvider<T> {
        private var instance: T? = null
        private val mutex = Mutex()

        override suspend fun invoke(): T = instance ?: mutex.withLock {
            if (instance == null) {
                instance = provider()
            }
            requireNotNull(instance)
        }
    }
}

fun interface ServiceProvider<T> {
    suspend operator fun invoke(): T
}

//abstract class ComponentProvider<Component> {
//
//    protected abstract fun provider(): Component
//
//    @Volatile
//    private var component: Component? = null
//
//    fun get(): Component = synchronized(this) {
//        component ?: provider().also { set(it) }
//    }
//
//    private fun set(newComponent: Component) {
//        component = newComponent
//    }
//}
