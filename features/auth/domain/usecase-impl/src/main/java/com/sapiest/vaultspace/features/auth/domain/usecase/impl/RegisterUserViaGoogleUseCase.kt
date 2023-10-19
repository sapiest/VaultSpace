package com.sapiest.vaultspace.features.auth.domain.usecase.impl

import com.sapiest.vaultspace.feature.auth.data.api.GoogleAuthRepository

class RegisterUserViaGoogleUseCase(
    private val repository: GoogleAuthRepository
) {
    suspend operator fun invoke() {
        TODO("sing up via google")
    }
}