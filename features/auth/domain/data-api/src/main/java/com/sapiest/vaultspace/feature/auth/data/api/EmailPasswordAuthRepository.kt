package com.sapiest.vaultspace.feature.auth.data.api

import com.sapiest.vaultspace.feature.auth.data.api.models.UserModel
import kotlinx.coroutines.flow.Flow

interface EmailPasswordAuthRepository {

    suspend fun registerUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<UserModel>>

    suspend fun loginWithEmailAndPassword(email: String, password: String): Flow<Result<UserModel>>
}