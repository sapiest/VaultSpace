package com.sapiest.vaultspace.di

import androidx.room.RoomDatabase
import com.sapiest.vaultspace.database.VaultSpaceDatabase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindDatabase(
        database: VaultSpaceDatabase
    ): RoomDatabase
}