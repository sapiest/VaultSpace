package com.sapiest.vaultspace.app.bind.di

import com.sapiest.vaultspace.feature.auth.data.api.EmailLinkAuthRepository
import com.sapiest.vaultspace.feature.auth.data.api.EmailPasswordAuthRepository
import com.sapiest.vaultspace.feature.auth.data.api.GoogleAuthRepository
import com.sapiest.vaultspace.features.auth.domain.usecase.impl.LoginViaEmailAndPasswordUseCase
import com.sapiest.vaultspace.features.auth.domain.usecase.impl.LoginViaEmailLinkUseCase
import com.sapiest.vaultspace.features.auth.domain.usecase.impl.LoginViaGoogleUseCase
import com.sapiest.vaultspace.features.auth.domain.usecase.impl.RegisterUserViaEmailAndPasswordUseCase
import com.sapiest.vaultspace.features.auth.domain.usecase.impl.RegisterUserViaGoogleUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppBindModule {
    @Provides
    @Reusable
    fun provideLoginViaEmailAndPasswordUseCase(
        repository: EmailPasswordAuthRepository
    ): LoginViaEmailAndPasswordUseCase = LoginViaEmailAndPasswordUseCase(repository)

    @Provides
    @Reusable
    fun provideLoginViaEmailLinkUseCase(
        repository: EmailLinkAuthRepository
    ): LoginViaEmailLinkUseCase = LoginViaEmailLinkUseCase(repository)

    @Provides
    @Reusable
    fun provideLoginViaGoogleUseCase(
        repository: GoogleAuthRepository
    ): LoginViaGoogleUseCase = LoginViaGoogleUseCase(repository)

    @Provides
    @Reusable
    fun provideRegisterUserViaEmailAndPasswordUseCase(
        repository: EmailPasswordAuthRepository
    ): RegisterUserViaEmailAndPasswordUseCase = RegisterUserViaEmailAndPasswordUseCase(repository)

    @Provides
    @Reusable
    fun provideRegisterUserViaGoogleUseCase(
        repository: GoogleAuthRepository
    ): RegisterUserViaGoogleUseCase = RegisterUserViaGoogleUseCase(repository)
}