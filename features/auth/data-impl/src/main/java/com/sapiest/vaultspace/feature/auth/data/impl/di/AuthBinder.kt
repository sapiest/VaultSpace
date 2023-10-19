package com.sapiest.vaultspace.feature.auth.data.impl.di

import com.sapiest.vaultspace.feature.auth.data.api.EmailLinkAuthRepository
import com.sapiest.vaultspace.feature.auth.data.api.EmailPasswordAuthRepository
import com.sapiest.vaultspace.feature.auth.data.api.GoogleAuthRepository
import com.sapiest.vaultspace.feature.auth.data.api.SignOutRepository
import com.sapiest.vaultspace.feature.auth.data.impl.repository.EmailLinkAuthRepositoryImpl
import com.sapiest.vaultspace.feature.auth.data.impl.repository.FirebaseEmailPasswordAuthRepositoryImpl
import com.sapiest.vaultspace.feature.auth.data.impl.repository.GoogleAuthRepositoryImpl
import com.sapiest.vaultspace.feature.auth.data.impl.repository.SignOutRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthBinder {

    @Binds
    @Reusable
    fun bindFirebaseEmailPasswordAuthRepository(
        firebaseEmailPasswordAuthRepository: FirebaseEmailPasswordAuthRepositoryImpl
    ): EmailPasswordAuthRepository

    @Binds
    @Reusable
    fun bindFirebaseGoogleAuthRepository(
        googleAuthRepository: GoogleAuthRepositoryImpl
    ): GoogleAuthRepository

    @Binds
    @Reusable
    fun bindSignOutRepository(
        signOutRepository: SignOutRepositoryImpl
    ): SignOutRepository
    
    @Binds
    @Reusable
    fun bindEmailLinkAuthRepository(
        emailLinkAuthRepository: EmailLinkAuthRepositoryImpl
    ): EmailLinkAuthRepository
}