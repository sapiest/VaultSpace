package com.sapiest.vaultspace.feature.auth.data.api

import kotlinx.coroutines.flow.Flow

interface SignOutRepository {

    suspend fun signOut(): Flow<Result<Unit>>
}