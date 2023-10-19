package com.sapiest.vaultspace.feature.auth.data.impl.repository

import com.sapiest.vaultspace.feature.auth.data.api.GoogleAuthRepository
import com.sapiest.vaultspace.feature.auth.data.api.SignOutRepository
import com.sapiest.vaultspace.feature.auth.data.api.models.UserModel
import com.sapiest.vaultspace.feature.auth.data.impl.local.UserLocalDataSource
import com.sapiest.vaultspace.feature.auth.data.impl.mappers.FirebaseModelResultToUserModelResultMapper
import com.sapiest.vaultspace.feature.auth.data.impl.remote.GoogleSignInRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GoogleAuthRepositoryImpl @Inject constructor(
    private val googleSignInRemoteDataSource: GoogleSignInRemoteDataSource,
    private val firebaseModelResultToUserModelResultMapper: FirebaseModelResultToUserModelResultMapper,
    private val userLocalDataSource: UserLocalDataSource,
    signOutRepositoryDelegated: SignOutRepository
) : GoogleAuthRepository, SignOutRepository by signOutRepositoryDelegated {

    override suspend fun signInWithGoogle(idToken: String?): Flow<Result<UserModel>> {
        return googleSignInRemoteDataSource.signInWithCredential(
            idToken
        ).map { firebaseUsrResult ->
            firebaseModelResultToUserModelResultMapper(firebaseUsrResult) { userModel ->
                userLocalDataSource.saveUser(userModel)
            }
        }
    }
}