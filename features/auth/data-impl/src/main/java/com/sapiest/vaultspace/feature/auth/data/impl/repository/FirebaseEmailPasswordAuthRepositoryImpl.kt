package com.sapiest.vaultspace.feature.auth.data.impl.repository

import com.sapiest.vaultspace.feature.auth.data.api.EmailPasswordAuthRepository
import com.sapiest.vaultspace.feature.auth.data.api.SignOutRepository
import com.sapiest.vaultspace.feature.auth.data.api.models.UserModel
import com.sapiest.vaultspace.feature.auth.data.impl.local.UserLocalDataSource
import com.sapiest.vaultspace.feature.auth.data.impl.mappers.FirebaseModelResultToUserModelResultMapper
import com.sapiest.vaultspace.feature.auth.data.impl.remote.FirebaseEmailPasswordAuthRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseEmailPasswordAuthRepositoryImpl @Inject constructor(
    private val emailPasswordAuthRemoteDataSource: FirebaseEmailPasswordAuthRemoteDataSource,
    private val firebaseModelResultToUserModelResultMapper: FirebaseModelResultToUserModelResultMapper,
    private val userLocalDataSource: UserLocalDataSource,
    signOutRepositoryDelegated: SignOutRepository
) : EmailPasswordAuthRepository, SignOutRepository by signOutRepositoryDelegated {

    override suspend fun registerUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<UserModel>> {
        return emailPasswordAuthRemoteDataSource.createUserWithEmailAndPassword(
            email, password
        ).map { firebaseUsrResult ->
            firebaseModelResultToUserModelResultMapper(firebaseUsrResult) { userModel ->
                userLocalDataSource.saveUser(userModel)
            }
        }
    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<UserModel>> {
        return emailPasswordAuthRemoteDataSource.signInWithEmailAndPassword(
            email, password
        ).map { firebaseUsrResult ->
            firebaseModelResultToUserModelResultMapper(firebaseUsrResult) { userModel ->
                userLocalDataSource.saveUser(userModel)
            }
        }
    }
}