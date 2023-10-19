package com.sapiest.vaultspace.features.auth.domain.usecase.impl

import com.sapiest.vaultspace.feature.auth.data.api.EmailLinkAuthRepository

class LoginViaEmailLinkUseCase(
    private val repository: EmailLinkAuthRepository
) {
    suspend operator fun invoke(email: String) = repository.signInWithEmailLink(email)
}