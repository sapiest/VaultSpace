package com.sapiest.vaultspace.core.resources.di

import com.sapiest.vaultspace.core.resources.ResourcesProvider
import com.sapiest.vaultspace.core.resources.ResourcesProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ResourceBinder {

    @Binds
    @Singleton
    fun bindResourceProvider(
        resourceProvider: ResourcesProviderImpl
    ): ResourcesProvider
}