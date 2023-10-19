package com.sapiest.vaultspace.features.auth.domain.usecase.impl

import com.sapiest.vaultspace.feature.auth.data.api.GoogleAuthRepository

class LoginViaGoogleUseCase(
    private val repository: GoogleAuthRepository
) {
    suspend operator fun invoke(idToken: String?) = repository.signInWithGoogle(idToken)
}