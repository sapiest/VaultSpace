package com.sapiest.vaultspace.feature.auth.data.impl.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseEmailPasswordAuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<FirebaseUser>> = flow {
        emit(kotlin.runCatching {
            requireNotNull(
                auth.createUserWithEmailAndPassword(email, password).await().user
            ) { "User is null" }
        })
    }

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<FirebaseUser>> = flow {
        emit(kotlin.runCatching {
            requireNotNull(
                auth.signInWithEmailAndPassword(email, password).await().user
            ) { "User is null" }
        })
    }
}