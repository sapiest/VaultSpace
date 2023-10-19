package com.sapiest.vaultspace.features.auth.domain.usecase.impl

import com.sapiest.vaultspace.feature.auth.data.api.EmailPasswordAuthRepository

class LoginViaEmailAndPasswordUseCase(
    private val repository: EmailPasswordAuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.loginWithEmailAndPassword(email, password)
}