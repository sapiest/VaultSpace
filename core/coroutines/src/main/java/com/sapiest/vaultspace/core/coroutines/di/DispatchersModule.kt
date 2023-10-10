package com.sapiest.vaultspace.core.coroutines.di

import com.sapiest.vaultspace.core.coroutines.AppCoroutineDispatchers
import com.sapiest.vaultspace.core.coroutines.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.sapiest.vaultspace.core.coroutines.AppCoroutineDispatchers.IO
import com.sapiest.vaultspace.core.coroutines.AppCoroutineDispatchers.Default
import com.sapiest.vaultspace.core.coroutines.AppCoroutineDispatchers.Main

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @Dispatcher(IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(Main)
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}