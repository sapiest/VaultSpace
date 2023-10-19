package com.sapiest.vaultspace.feature.auth.data.impl.repository

import com.sapiest.vaultspace.feature.auth.data.api.SignOutRepository
import com.sapiest.vaultspace.feature.auth.data.impl.local.UserLocalDataSource
import com.sapiest.vaultspace.feature.auth.data.impl.remote.SignOutDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SignOutRepositoryImpl @Inject constructor(
    private val signOutDataSource: SignOutDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : SignOutRepository {

    override suspend fun signOut(): Flow<Result<Unit>> =
        signOutDataSource.signOut().flatMapLatest { result ->
            if (result.isSuccess) {
                userLocalDataSource.signOut()
            } else flowOf(Result.failure(result.exceptionOrNull() ?: Exception("Unknown error")))
        }
}