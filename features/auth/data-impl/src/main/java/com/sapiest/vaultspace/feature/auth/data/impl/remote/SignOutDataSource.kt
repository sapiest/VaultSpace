package com.sapiest.vaultspace.feature.auth.data.impl.remote

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignOutDataSource @Inject constructor(
    private val auth: FirebaseAuth,
) {

    suspend fun signOut(): Flow<Result<Unit>> = flow {
        emit(kotlin.runCatching {
            auth.signOut()
        })
    }
}