package com.sapiest.vaultspace.feature.auth.data.impl.remote

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


interface GoogleSignInRemoteDataSource {

    suspend fun signInWithCredential(idToken: String?): Flow<Result<FirebaseUser>>

    fun signInWithGoogle(): Intent
}
class GoogleSignInRemoteDataSourceImpl @AssistedInject constructor(
    @Assisted private val defaultWebClientId: String,
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth,
): GoogleSignInRemoteDataSource {

    override fun signInWithGoogle(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(defaultWebClientId)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)

//        val signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(defaultWebClientId)
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            .build()

        return googleSignInClient.signInIntent
    }

    override suspend fun signInWithCredential(idToken: String?): Flow<Result<FirebaseUser>> = flow {
        emit(runCatching {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            requireNotNull(auth.signInWithCredential(firebaseCredential).await().user) {
                "User not found"
            }
        })

    }

    @AssistedFactory
    interface Factory {
        fun create(defaultWebClientId: String): GoogleSignInRemoteDataSourceImpl
    }
}