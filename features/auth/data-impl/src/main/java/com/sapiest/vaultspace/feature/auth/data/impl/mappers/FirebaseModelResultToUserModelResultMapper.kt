package com.sapiest.vaultspace.feature.auth.data.impl.mappers

import com.google.firebase.auth.FirebaseUser
import com.sapiest.vaultspace.feature.auth.data.api.models.UserModel
import javax.inject.Inject

class FirebaseModelResultToUserModelResultMapper @Inject constructor() {
    suspend operator fun invoke(
        firebaseUsrResult: Result<FirebaseUser>,
        successProcessing: suspend (UserModel) -> Unit = { }
    ): Result<UserModel> {
        return if (firebaseUsrResult.isSuccess) {
            val firebaseUser =
                firebaseUsrResult.getOrNull()
                    ?: return Result.failure(NullPointerException("User not found"))
            val userModel = UserModel(
                id = firebaseUser.uid,
                name = firebaseUser.displayName,
                email = firebaseUser.email,
                uriString = firebaseUser.photoUrl?.toString()
            )
            successProcessing.invoke(userModel)
            Result.success(userModel)
        } else Result.failure(firebaseUsrResult.exceptionOrNull() ?: Exception("Unknown error"))
    }
}