package com.sapiest.vaultspace.feature.auth.data.api

import com.sapiest.vaultspace.feature.auth.data.api.models.UserModel
import kotlinx.coroutines.flow.Flow

interface GoogleAuthRepository {

    suspend fun signInWithGoogle(idToken: String?): Flow<Result<UserModel>>
}