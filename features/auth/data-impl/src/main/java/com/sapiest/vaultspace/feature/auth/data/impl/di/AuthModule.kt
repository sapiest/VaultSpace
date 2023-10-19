package com.sapiest.vaultspace.feature.auth.data.impl.di


import com.google.firebase.auth.FirebaseAuth
import com.sapiest.vaultspace.feature.auth.data.impl.remote.GoogleSignInRemoteDataSource
import com.sapiest.vaultspace.feature.auth.data.impl.remote.GoogleSignInRemoteDataSourceImpl
import com.sapiest.vaultspace.features.auth.data.impl.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInRemoteDataSource(
        googleSignInRemoteDataSourceFactory: GoogleSignInRemoteDataSourceImpl.Factory
    ): GoogleSignInRemoteDataSource = googleSignInRemoteDataSourceFactory.create(
        BuildConfig.OAUTH_CLIENT_ID
    )
}