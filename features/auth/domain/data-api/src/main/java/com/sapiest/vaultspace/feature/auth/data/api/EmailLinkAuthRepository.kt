package com.sapiest.vaultspace.feature.auth.data.api

interface EmailLinkAuthRepository {

    suspend fun signInWithEmailLink(email: String)
}