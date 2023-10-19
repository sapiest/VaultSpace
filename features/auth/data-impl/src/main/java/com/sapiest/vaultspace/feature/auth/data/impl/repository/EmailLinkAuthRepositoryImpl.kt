package com.sapiest.vaultspace.feature.auth.data.impl.repository

import com.sapiest.vaultspace.feature.auth.data.api.EmailLinkAuthRepository
import com.sapiest.vaultspace.feature.auth.data.api.SignOutRepository
import javax.inject.Inject

class EmailLinkAuthRepositoryImpl @Inject constructor(
    signOutRepositoryDelegated: SignOutRepository
) : EmailLinkAuthRepository, SignOutRepository by signOutRepositoryDelegated {

    override suspend fun signInWithEmailLink(email: String) {
        TODO("Not yet implemented")
    }
}